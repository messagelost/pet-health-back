package com.jacob.service.petData.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.model.petData.dto.PetEventDto;
import com.jacob.common.model.petData.entity.PetEvent;
import com.jacob.common.model.petData.entity.PetEventReminder;
import com.jacob.common.model.petData.enums.EventStatusEnum;
import com.jacob.common.model.system.entity.NotifyTemplate;
import com.jacob.common.model.user.entity.SysUserNotifySetting;
import com.jacob.common.redis.RedisConstant;
import com.jacob.common.redis.RedisUtils;
import com.jacob.common.utils.CronUtils;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petData.PetEventDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petData.PetEventService;
import com.jacob.service.petData.SysUserNotifySettingService;
import com.jacob.service.system.NotifyTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PetEventServiceImpl extends BaseServiceImpl<PetEventDao, PetEvent> implements PetEventService {

    @Autowired
    private PetEventDao petEventDao;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SysUserNotifySettingService sysUserNotifySettingService;
    @Autowired
    private NotifyTemplateService notifyTemplateService;

    @Override
    public SqlDao getDao() {
        return petEventDao;
    }

    @Override
    public void addTask(String reminderId, long triggerTime) {
        redisUtils.zAdd(RedisConstant.NOTIFY_SCHEDULE.getCode(), reminderId, triggerTime);
    }

    @Override
    public void addEvent(PetEventDto eventDto) {
        String userId = jwtUtil.getCurrentUserId();
        // 新增预约
        PetEvent petEvent = new PetEvent();
        petEvent.setEventId(snowflakeIdGenerator.generateIdWithPrefix("E"));
        petEvent.setPetId(eventDto.getPetId());
        petEvent.setUserId(userId);
        petEvent.setEventType(eventDto.getEventType());
        petEvent.setEventContent(eventDto.getEventContent());
        petEvent.setAppointmentTime(eventDto.getAppointmentTime());
        insertWithBean(petEvent);
        // 新增预约提醒类型
        // 查询用户有几种提醒方式
        List<String> configList = sysUserNotifySettingService.list(
                new LambdaQueryWrapper<SysUserNotifySetting>()
                        .eq(SysUserNotifySetting::getUserId, userId)
                        .eq(SysUserNotifySetting::getEnabled, 1)
        ).stream().map(SysUserNotifySetting::getChannel).filter(String::isEmpty).toList();
        // 事件类型-提醒模板 映射
        Map<Integer, String> templateMap = notifyTemplateService.list().stream().collect(Collectors.toMap(NotifyTemplate::getEventType, NotifyTemplate::getTemplateId));
        // 每种提醒方式新增一条提醒规则
        configList.forEach(channel -> {
            PetEventReminder petEventReminder = new PetEventReminder();
            petEventReminder.setReminderId(snowflakeIdGenerator.generateIdWithPrefix("R"));
            petEventReminder.setEventId(petEvent.getEventId());
            petEventReminder.setUserId(userId);
            petEventReminder.setChannel(channel);
            petEventReminder.setTemplateId(templateMap.get(eventDto.getEventType()));
            petEventReminder.setRemindType(eventDto.getRemindType());
            petEventReminder.setCronExpr(eventDto.getCronExpr());
            petEventReminder.setNextTriggerTime(CronUtils.next(eventDto.getCronExpr()));
            insertWithBean(petEventReminder);

            addTask(petEventReminder.getReminderId(), petEventReminder.getNextTriggerTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        });
    }

    @Override
    public void updateEvent(PetEvent event) {
        if(event.getStatus().equals(EventStatusEnum.CANCELLED.getCode())){
            // 取消预约 移除调度
            redisUtils.zRemove(RedisConstant.NOTIFY_SCHEDULE.getCode(), event.getEventId());
        }
        updateWithBean(event);
    }
}
