package com.jacob.common.model.petConfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 宠物营养成分配置实体类
 * 对应数据库表：pet_nutrition_config
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_nutrition_config")
public class PetNutritionConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配置ID（主键）
     */
    @TableId
    private String configId;

    /**
     * 父级类别ID（关联自身表的config_id，顶级为-1或空）
     */
    @TableField
    private String parentId;

    /**
     * 营养成分
     */
    @TableField
    private String nutritionName;

    /**
     * 单位（整型：对应字典码值）
     */
    @TableField
    private Integer unit;

    /**
     * 非数据库字段：子级营养成分列表（用于树形结构展示）
     */
    @TableField(exist = false)
    private List<PetNutritionConfig> children;

    /**
     * 非数据库字段：是否有子级
     */
    @TableField(exist = false)
    private boolean hasChildren;

}