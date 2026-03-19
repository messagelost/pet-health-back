package com.jacob.service.notify.sender;

import com.jacob.common.model.petData.entity.PetEventReminder;
import com.jacob.common.model.user.entity.SysUserNotifyMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsSender extends BaseSender{

    public void send(PetEventReminder reminder) {
        // TODO: 发送短信
        log.info("发送短信：{}", reminder.getReminderId());
        SysUserNotifyMsg msg = getMsg(reminder);
    }
}
