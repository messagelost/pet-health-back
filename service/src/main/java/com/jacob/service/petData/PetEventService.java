package com.jacob.service.petData;

import com.jacob.common.model.petData.dto.PetEventDto;
import com.jacob.common.model.petData.entity.PetEvent;
import com.jacob.service.base.BaseService;

public interface PetEventService extends BaseService<PetEvent> {

    /**
     * 新增Redis调度任务
     * @param reminderId 提醒规则ID
     * @param triggerTime 触发时间
     */
    void addTask(String reminderId, long triggerTime);

    /**
     * 新增宠物预约
     * @param eventDto 宠物预约DTO
     */
    PetEvent addEvent(PetEventDto eventDto);

    /**
     * 修改宠物预约
     * @param event 宠物预约实体
     */
    void updateEvent(PetEvent event);
}
