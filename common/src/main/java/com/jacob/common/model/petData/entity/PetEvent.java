package com.jacob.common.model.petData.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 宠物预约实体类
 * 对应数据库表：pet_event
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_event")
public class PetEvent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 预约ID（主键）
     */
    @TableId
    private String eventId;

    /**
     * 用户ID（关联系统用户表）
     */
    @TableField
    private String userId;

    /**
     * 宠物ID（关联宠物信息表）
     */
    @TableField
    private String petId;

    /**
     * 事件类型
     */
    @TableField
    private Integer eventType;

    /**
     * 事件内容
     */
    @TableField
    private String eventContent;

    /**
     * 预约时间
     */
    @TableField
    private LocalDateTime appointmentTime;

    /**
     * 预约状态（0=待提醒，1=已提醒，2=已取消）
     */
    @TableField
    private Integer status;

    /**
     * 非数据库字段：宠物名称
     */
    @TableField(exist = false)
    private String petName;

}