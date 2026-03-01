package com.jacob.common.model.petConfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 宠物种类实体类
 * 对应数据库表：pet_species
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_species")
public class PetSpecies extends BaseEntity {

    /**
     * 宠物种类ID（主键）
     */
    @TableId
    private String speciesId;

    /**
     * 宠物种类名
     */
    @TableField
    private String speciesName;

    /**
     * 描述
     */
    @TableField
    private String description;

    /**
     * 图标
     */
    @TableField
    private String icon;

    /**
     * 生命周期阶段
     */
    @TableField(exist = false)
    private List<PetLifeStage> lifeStageList;

}