package com.jacob.common.model.author.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_role")
public class SysUserRole extends BaseEntity {

    /**
     * 用户角色ID（主键）
     */
    @TableId
    private String userRoleId;

    /**
     * 用户ID
     */
    @TableField
    private String userId;

    /**
     * 角色ID
     */
    @TableField
    private String roleId;

    /**
     * 角色字符串
     */
    @TableField(exist = false)
    private String roleStr;

}
