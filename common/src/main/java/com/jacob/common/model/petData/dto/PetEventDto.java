package com.jacob.common.model.petData.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PetEventDto {

    /**
     * 宠物ID
     */
    private String petId;

    /**
     * 事件类型
     */
    private Integer eventType;

    /**
     * 事件内容
     */
    private String eventContent;

    /**
     * 预约时间
     */
    private LocalDateTime appointmentTime;

    /**
     * 提醒类型
     */
    private String remindType;

    /**
     * Cron表达式
     */
    private String cronExpr;
}
