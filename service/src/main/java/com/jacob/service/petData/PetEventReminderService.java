package com.jacob.service.petData;

import com.jacob.common.model.petData.entity.PetEventReminder;
import com.jacob.service.base.BaseService;

public interface PetEventReminderService extends BaseService<PetEventReminder> {
    void process(String reminderId);
}
