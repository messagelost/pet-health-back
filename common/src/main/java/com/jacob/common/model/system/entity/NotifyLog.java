package com.jacob.common.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 通知日志实体类
 * 对应数据库表：notify_log
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("notify_log")
public class NotifyLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日志ID（主键）
     */
    @TableId
    private String logId;

    /**
     * 用户ID（关联系统用户表）
     */
    @TableField
    private String userId;

    /**
     * 预约ID（关联宠物预约表）
     */
    @TableField
    private String appointmentId;

    /**
     * 通知渠道（如：wechat/sms/msg）
     */
    @TableField
    private String channel;

    /**
     * 通知内容
     */
    @TableField
    private String content;

    /**
     * 发送状态
     */
    @TableField
    private Integer sendStatus;

    /**
     * 发送时间
     */
    @TableField
    private LocalDate sendTime;

}