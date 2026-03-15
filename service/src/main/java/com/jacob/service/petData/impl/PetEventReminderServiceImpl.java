package com.jacob.service.petData.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.model.petData.dto.PetEventDto;
import com.jacob.common.model.petData.entity.PetEvent;
import com.jacob.common.model.petData.entity.PetEventReminder;
import com.jacob.common.model.petData.enums.EventStatusEnum;
import com.jacob.common.model.petData.enums.ReminderTypeEnum;
import com.jacob.common.model.system.entity.NotifyTemplate;
import com.jacob.common.model.user.entity.SysUserNotifySetting;
import com.jacob.common.redis.RedisConstant;
import com.jacob.common.redis.RedisUtils;
import com.jacob.common.utils.CronUtils;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petData.PetEventReminderDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.notify.NotificationService;
import com.jacob.service.petData.PetEventReminderService;
import com.jacob.service.petData.PetEventService;
import com.jacob.service.petData.SysUserNotifySettingService;
import com.jacob.service.system.NotifyTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PetEventReminderServiceImpl extends BaseServiceImpl<PetEventReminderDao, PetEventReminder> implements PetEventReminderService {

    @Autowired
    private PetEventReminderDao petEventReminderDao;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private PetEventService petEventService;
    @Autowired
    private SysUserNotifySettingService sysUserNotifySettingService;
    @Autowired
    private NotifyTemplateService notifyTemplateService;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public SqlDao getDao() {
        return petEventReminderDao;
    }

    @Override
    public void process(String reminderId) {
        PetEventReminder reminder = getById(reminderId);
        PetEvent event = petEventService.getById(reminder.getEventId());
        notificationService.send(reminder);
        if(ReminderTypeEnum.REPEAT.getCode().equals(reminder.getRemindType())){
            LocalDateTime next = CronUtils.next(reminder.getCronExpr());
            // TODO 提前提醒时间
            petEventService.addTask(reminderId, next.toInstant(ZoneOffset.of("+8")).toEpochMilli());
            reminder.setNextTriggerTime(next);

            event.setAppointmentTime(next);
        }else {
            event.setStatus(EventStatusEnum.REMINDED.getCode());
        }
        updateById(reminder);
        petEventService.updateById(event);
    }

    @Override
    public void addReminder(PetEventDto eventDto, String eventId) {
        String userId = jwtUtil.getCurrentUserId();
        // 新增预约提醒类型
        // 查询用户有几种提醒方式
        List<String> configList = sysUserNotifySettingService.getConfigChannel(userId);
        // 事件类型-提醒模板 映射
        Map<Integer, String> templateMap = notifyTemplateService.list().stream().collect(Collectors.toMap(NotifyTemplate::getEventType, NotifyTemplate::getTemplateId));
        // 每种提醒方式新增一条提醒规则
        configList.forEach(channel -> {
            PetEventReminder petEventReminder = new PetEventReminder();
            petEventReminder.setReminderId(snowflakeIdGenerator.generateIdWithPrefix("R"));
            petEventReminder.setEventId(eventId);
            petEventReminder.setUserId(userId);
            petEventReminder.setChannel(channel);
            petEventReminder.setTemplateId(templateMap.get(eventDto.getEventType()));
            petEventReminder.setRemindType(eventDto.getRemindType());
            petEventReminder.setCronExpr(eventDto.getCronExpr());
            petEventReminder.setNextTriggerTime(CronUtils.next(eventDto.getCronExpr()));
            petEventReminder.setCreateUserId(userId);
            petEventReminder.setUpdateUserId(userId);
            insertWithBean(petEventReminder);

            // TODO 提前提醒时间
            petEventService.addTask(petEventReminder.getReminderId(), petEventReminder.getNextTriggerTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        });
    }

    @Override
    public void deleteReminder(String eventId) {
        // 根据事件id查询提醒规则
        List<String> idList = list(new LambdaQueryWrapper<PetEventReminder>().eq(PetEventReminder::getEventId, eventId)).stream().map(PetEventReminder::getReminderId).toList();
        // 在数据库中移除
        remove(new LambdaQueryWrapper<PetEventReminder>().in(PetEventReminder::getReminderId, idList));
        // 在调度中移除
        redisUtils.zRemove(RedisConstant.NOTIFY_SCHEDULE.getCode(), idList.toArray());
    }

    @Override
    public void updateReminder(PetEventDto eventDto) {
        String userId = jwtUtil.getCurrentUserId();
        // 查询用户有几种提醒方式
        List<String> configList = sysUserNotifySettingService.getConfigChannel(userId);
        // 事件类型-提醒模板 映射
        Map<Integer, String> templateMap = notifyTemplateService.list().stream().collect(Collectors.toMap(NotifyTemplate::getEventType, NotifyTemplate::getTemplateId));
        // 查询提醒规则
        List<PetEventReminder> reminderList = list(new LambdaQueryWrapper<PetEventReminder>().eq(PetEventReminder::getEventId, eventDto.getEventId()));
        reminderList.forEach(reminder -> {
            reminder.setRemindType(eventDto.getRemindType());
            reminder.setCronExpr(eventDto.getCronExpr());
            reminder.setTemplateId(templateMap.get(eventDto.getEventType()));
            reminder.setNextTriggerTime(CronUtils.next(eventDto.getCronExpr()));
            reminder.setUpdateUserId(userId);
            updateById(reminder);

            // 移除调度
            redisUtils.zRemove(RedisConstant.NOTIFY_SCHEDULE.getCode(), reminder.getReminderId());
            // 添加任务
            petEventService.addTask(reminder.getReminderId(), reminder.getNextTriggerTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        });
    }
}
