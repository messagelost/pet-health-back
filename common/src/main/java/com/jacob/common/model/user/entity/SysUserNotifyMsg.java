package com.jacob.common.model.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 用户通知实体类
 * 对应数据库表：sys_user_notify_msg
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_notify_msg")
public class SysUserNotifyMsg extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @TableId
    private String msgId;

    /**
     * 用户ID
     */
    @TableField
    private String userId;

    /**
     * 标题
     */
    @TableField
    private String title;

    /**
     * 内容
     */
    @TableField
    private String content;

    /**
     * 状态
     */
    @TableField
    private Integer status;
}
