package com.jacob.common.model.petData.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime appointmentTime;

    /**
     * 提醒类型
     */
    private String remindType;

    /**
     * Cron表达式
     */
    private String cronExpr;

    /**
     * 事件id
     */
    private String eventId;
}
