package com.jacob.common.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知模板实体类
 * 对应数据库表：notify_template
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("notify_template")
public class NotifyTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 模板ID（主键）
     */
    @TableId
    private String templateId;

    /**
     * 事件类型
     */
    @TableField
    private Integer eventType;

    /**
     * 通知渠道
     */
    @TableField
    private String channel;

    /**
     * 标题模板（支持参数占位符，如：{petName}的{eventType}预约提醒）
     */
    @TableField
    private String titleTemplate;

    /**
     * 正文模板（支持参数占位符）
     */
    @TableField
    private String contentTemplate;

    /**
     * 参数列表（JSON格式，如：["petName","appointmentTime","eventType"]）
     */
    @TableField
    private String params;

}