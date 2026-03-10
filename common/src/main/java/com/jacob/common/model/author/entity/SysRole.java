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
@TableName("sys_role")
public class SysRole extends BaseEntity {

    /**
     * 角色ID（主键）
     */
    @TableId
    private String roleId;

    /**
     * 角色名称
     */
    @TableField
    private String roleName;

    /**
     * 角色字符串
     */
    @TableField
    private String roleStr;

    /**
     * 角色排序
     */
    @TableField
    private Integer roleSort;


    @TableField(exist = false)
    private List<String> menuIdList;

}
