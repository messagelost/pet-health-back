package com.jacob.common.model.petConfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 宠物生命周期阶段实体类
 * 对应数据库表：pet_life_stage
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_life_stage")
public class PetLifeStage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 阶段ID（主键）
     */
    @TableId
    private String stageId;

    /**
     * 种类ID（关联宠物种类表species_id）
     */
    @TableField
    private String speciesId;

    /**
     * 阶段名称
     */
    @TableField
    private String stageName;

    /**
     * 最小月龄
     */
    @TableField
    private Integer minAgeMonth;

    /**
     * 最大月龄（-1表示无上限）
     */
    @TableField
    private Integer maxAgeMonth;

    /**
     * 阶段描述
     */
    @TableField
    private String description;

    /**
     * 排序字段（数值越小越靠前）
     */
    @TableField
    private Integer sort;

    /**
     * 非数据库字段：种类名称（前端展示用）
     */
    @TableField(exist = false)
    private String speciesName;

}