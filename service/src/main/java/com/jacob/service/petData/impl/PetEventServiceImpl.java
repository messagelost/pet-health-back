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

    @Override
    public SqlDao getDao() {
        return petEventDao;
    }

    @Override
    public void addTask(String reminderId, long triggerTime) {
        redisUtils.zAdd(RedisConstant.NOTIFY_SCHEDULE.getCode(), reminderId, triggerTime);
    }

    @Override
    public PetEvent addEvent(PetEventDto eventDto) {
        String userId = jwtUtil.getCurrentUserId();
        // 新增预约
        PetEvent petEvent = new PetEvent();
        petEvent.setEventId(snowflakeIdGenerator.generateIdWithPrefix("E"));
        petEvent.setPetId(eventDto.getPetId());
        petEvent.setUserId(userId);
        petEvent.setEventType(eventDto.getEventType());
        petEvent.setEventContent(eventDto.getEventContent());
        petEvent.setAppointmentTime(eventDto.getAppointmentTime());
        petEvent.setCreateUserId(userId);
        petEvent.setUpdateUserId(userId);
        insertWithBean(petEvent);

        return petEvent;
    }

    @Override
    public void updateEvent(PetEvent event) {
        updateWithBean(event);
    }
}
