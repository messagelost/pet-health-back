package com.jacob.common.model.petData.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 宠物事件调度实体类
 * 对应数据库表：pet_event
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_event")
public class PetEvent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String eventId;

    /**
     * 宠物ID（关联pet_basic_info表的pet_id）
     */
    @TableField
    private String petId;

    /**
     * 事件类型（1=疫苗接种，2=驱虫，3=洗护，4=用药，5=体检，6=喂食提醒，7=其他）
     */
    @TableField
    private Integer eventType;

    /**
     * 事件名称
     */
    @TableField
    private String eventName;

    /**
     * 下次应执行的日期
     */
    @TableField
    private LocalDate scheduleDate;

    /**
     * 提前提醒天数
     */
    @TableField
    private Integer remindBeforeDays;

    /**
     * 提醒状态（0=未提醒，1=已提醒，2=已完成，3=已取消）
     */
    @TableField
    private Integer status;

    /**
     * 是否周期任务（0=否，1=是）
     */
    @TableField
    private Integer isRepeat;

    /**
     * 频率
     */
    @TableField
    private Integer frequency;

    /**
     * 周期结束时间
     */
    @TableField
    private LocalDate endDate;

    /**
     * 最后执行时间
     */
    @TableField
    private LocalDate lastExecuteDate;

    /**
     * 通知渠道ID
     */
    @TableField
    private String channelId;

}