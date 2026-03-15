package com.jacob.service.petData;

import com.jacob.common.model.petData.dto.PetEventDto;
import com.jacob.common.model.petData.entity.PetEventReminder;
import com.jacob.service.base.BaseService;

public interface PetEventReminderService extends BaseService<PetEventReminder> {
    void process(String reminderId);

    void addReminder(PetEventDto eventDto, String eventId);

    void deleteReminder(String id);

    void updateReminder(PetEventDto eventDto);
}
