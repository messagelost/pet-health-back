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
@TableName("sys_menu_role")
public class SysMenuRole extends BaseEntity {

    /**
     * 权限角色ID（主键）
     */
    @TableId
    private String menuRoleId;

    /**
     * 菜单id
     */
    @TableField
    private String menuId;

    /**
     * 角色ID
     */
    @TableField
    private String roleId;

}
