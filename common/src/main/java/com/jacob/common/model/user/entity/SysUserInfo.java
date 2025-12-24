package com.jacob.common.model.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_info")
public class SysUserInfo extends BaseEntity {


    /**
     * 用户id（主键）
     */
    @TableId
    private String userId;

    /**
     * 用户名（唯一）
     */
    @TableField
    private String username;

    /**
     * 密码（加密存储）
     */
    @TableField
    private String password;

    /**
     * 昵称
     */
    @TableField
    private String nickname;

    /**
     * 头像URL
     */
    @TableField
    private String avatar;

    /**
     * 电话号码
     */
    @TableField
    private String phone;

    /**
     * 邮箱
     */
    @TableField
    private String email;

    /**
     * 性别（0-未知 1-男 2-女）
     */
    @TableField
    private Integer gender;

    /**
     * 生日
     */
    @TableField
    private LocalDate birthday;

    /**
     * 状态（1-正常 0-禁用）
     */
    @TableField
    private Integer status;

    /**
     * 最后登录时间
     */
    @TableField
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    @TableField
    private String lastLoginIp;

}
