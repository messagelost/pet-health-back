package com.jacob.common.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 通知执行日志实体类
 * 对应数据库表：notification_log
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("notification_log")
public class NotificationLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日志主键ID
     */
    @TableId
    private String logId;

    /**
     * 关联的任务ID
     */
    @TableField
    private String taskId;

    /**
     * 接收通知的用户ID
     */
    @TableField
    private String userId;

    /**
     * 通知类型
     */
    @TableField
    private String notificationType;

    /**
     * 渠道名称
     */
    @TableField
    private String channel;

    /**
     * 通知标题
     */
    @TableField
    private String title;

    /**
     * 通知内容
     */
    @TableField
    private String content;

    /**
     * 发送时间（精确到时分秒，适配日志场景）
     */
    @TableField
    private LocalDateTime sendTime;

    /**
     * 发送状态
     */
    @TableField
    private Integer status;

    /**
     * 重试次数
     */
    @TableField
    private Integer retryCount;

    /**
     * 第三方返回信息（如短信平台的回执、错误码等）
     */
    @TableField
    private String responseMsg;


}