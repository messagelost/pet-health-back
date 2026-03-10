package com.jacob.common.model.petData.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 宠物预约提醒规则实体类
 * 对应数据库表：pet_event_reminder
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_event_reminder")
public class PetEventReminder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 提醒规则ID（主键）
     */
    @TableId
    private String reminderId;

    /**
     * 预约ID（关联宠物预约表pet_event）
     */
    @TableField
    private String eventId;

    /**
     * 用户ID（关联系统用户表）
     */
    @TableField
    private String userId;

    /**
     * 提醒渠道
     */
    @TableField
    private String channel;

    /**
     * 模板ID（关联消息模板表）
     */
    @TableField
    private String templateId;

    /**
     * 提醒类型（ once/repeat ）
     */
    @TableField
    private String remindType;

    /**
     * Cron表达式
     */
    @TableField
    private String cronExpr;

    /**
     * 下次执行时间
     */
    @TableField
    private LocalDateTime nextTriggerTime;

    /**
     * 提醒状态（0=禁用，1=启用）
     */
    @TableField
    private Integer status;

    // ===================== 联表查询字段 =====================
    /**
     * 预约事件内容（关联pet_event）
     */
    @TableField(exist = false)
    private String eventContent;

    /**
     * 预约时间（关联pet_event）
     */
    @TableField(exist = false)
    private LocalDateTime appointmentTime;

}