package com.jacob.common.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知渠道配置实体类
 * 对应数据库表：notification_channel_config
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("notification_channel_config")
public class NotificationChannelConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 渠道ID（主键）
     */
    @TableId
    private String channelId;

    /**
     * 渠道名称（短信、邮件、站内推送）
     */
    @TableField
    private String channel;

    /**
     * 供应者（阿里云短信、腾讯云短信、QQ邮箱）
     */
    @TableField
    private String provider;

    /**
     * 配置内容JSON（如：{"appKey":"xxx","appSecret":"xxx","templateId":"xxx"}）
     */
    @TableField
    private String configJson;

    /**
     * 启用状态
     */
    @TableField
    private Integer status;


}