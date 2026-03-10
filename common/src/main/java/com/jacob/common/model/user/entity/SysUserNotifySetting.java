package com.jacob.common.model.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户通知配置实体类
 * 对应数据库表：user_notify_setting
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_notify_setting")
public class SysUserNotifySetting extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配置ID（主键）
     */
    @TableId
    private String settingId;

    /**
     * 用户ID（关联系统用户表）
     */
    @TableField
    private String userId;

    /**
     * 通知渠道（如：site/sms/msg）
     */
    @TableField
    private String channel;

    /**
     * 是否开启（0=关闭，1=开启）
     */
    @TableField
    private Integer enabled;

    // ===================== 非数据库字段（前端展示用） =====================
    /**
     * 用户名（关联sys_user）
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 渠道名称（字典解析值）
     */
    @TableField(exist = false)
    private String channelLabel;

    /**
     * 启用状态名称（字典解析值）
     */
    @TableField(exist = false)
    private String enabledLabel;

}