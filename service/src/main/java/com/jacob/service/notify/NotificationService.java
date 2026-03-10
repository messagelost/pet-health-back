package com.jacob.service.notify;

import com.jacob.common.model.petData.entity.PetEventReminder;
import com.jacob.common.model.petData.enums.ChannelType;
import com.jacob.service.notify.sender.EmailSender;
import com.jacob.service.notify.sender.SiteSender;
import com.jacob.service.notify.sender.SmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通知服务
 */
@Service
public class NotificationService {

    @Autowired
    private EmailSender emailSender;
    @Autowired
    private SiteSender siteSender;
    @Autowired
    private SmsSender smsSender;

    public void send(PetEventReminder reminder) {
        switch (reminder.getChannel()) {
            case "email":
                emailSender.send(reminder);
                break;
            case "site":
                siteSender.send(reminder);
                break;
            case "sms":
                smsSender.send(reminder);
                break;
        }
    }
}
