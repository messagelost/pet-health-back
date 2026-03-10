package com.jacob.common.model.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 用户日程实体类
 * 对应数据库表：sys_user_schedule
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_schedule")
public class SysUserSchedule extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日程ID
     */
    @TableId
    private String scheduleId;

    /**
     * 用户ID
     */
    @TableField
    private String userId;

    /**
     * 宠物ID
     */
    @TableField
    private String petId;

    /**
     * 日程日期
     */
    @TableField
    private LocalDate scheduleDate;

    /**
     * 日程时间
     */
    @TableField
    private LocalTime scheduleTime;

    /**
     * 日程内容
     */
    @TableField
    private String content;

    /**
     * 日程类型（同 eventType ）
     */
    @TableField
    private Integer scheduleType;

    /**
     * 状态（0=未完成，1=已完成）
     */
    @TableField
    private Integer status;


    /**
     * 非数据库字段：宠物名称（前端展示用）
     */
    @TableField(exist = false)
    private String petName;


}
