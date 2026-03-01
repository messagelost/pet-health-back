package com.jacob.common.model.author.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class SysMenu extends BaseEntity {

    /**
     * 菜单ID（主键）
     */
    @TableId
    private String menuId;

    /**
     * 父级菜单ID
     */
    @TableField
    private String parentId;

    /**
     * 菜单名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 菜单标题
     */
    @TableField
    private String title;

    /**
     * 菜单类型（1-目录 2-菜单 3-按钮）
     */
    @TableField
    private Integer menuType;

    /**
     * 路由路径
     */
    @TableField(value = "`path`")
    private String path;

    /**
     * 激活菜单
     */
    @TableField
    private String activeMenu;

    /**
     * 重定向路由路径
     */
    @TableField
    private String redirect;

    /**
     * 前端组件路径
     */
    @TableField
    private String component;

    /**
     * 权限标识
     */
    @TableField
    private String authorStr;

    /**
     * 菜单图标
     */
    @TableField
    private String icon;

    /**
     * 排序
     */
    @TableField(value = "`order`")
    private Integer order;

    /**
     * 是否显示（1-显示 0-隐藏）
     */
    @TableField
    private Integer visible;

    /**
     * 状态（1-启用 0-禁用）
     */
    @TableField
    private Integer status;

    /**
     * 有子项
     */
    @TableField(exist = false)
    private Boolean hasChildren;

}
