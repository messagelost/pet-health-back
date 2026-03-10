package com.jacob.service.petData.impl;

import com.jacob.common.model.petData.entity.PetEventReminder;
import com.jacob.common.model.petData.enums.EventStatusEnum;
import com.jacob.common.model.petData.enums.ReminderTypeEnum;
import com.jacob.common.redis.RedisUtils;
import com.jacob.common.utils.CronUtils;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petData.PetEventReminderDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.notify.NotificationService;
import com.jacob.service.petData.PetEventReminderService;
import com.jacob.service.petData.PetEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

    @Override
    public SqlDao getDao() {
        return petEventReminderDao;
    }

    @Override
    public void process(String reminderId) {
        PetEventReminder reminder = getById(reminderId);
        notificationService.send(reminder);
        if(ReminderTypeEnum.REPEAT.getCode().equals(reminder.getRemindType())){
            LocalDateTime next = CronUtils.next(reminder.getCronExpr());
            petEventService.addTask(reminderId, next.toInstant(ZoneOffset.of("+8")).toEpochMilli());
            reminder.setNextTriggerTime(next);
        }
        reminder.setStatus(EventStatusEnum.REMINDED.getCode());
        updateById(reminder);
    }
}
