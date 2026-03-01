package com.jacob.common.model.petConfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 宠物品种实体类
 * 对应数据库表：pet_breed
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_breed")
public class PetBreed extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 品种ID（主键）
     */
    @TableId
    private String breedId;

    /**
     * 种类ID（关联pet_species表的species_id）
     */
    @TableField
    private String speciesId;

    /**
     * 品种名
     */
    @TableField
    private String breedName;

    /**
     * 描述
     */
    @TableField
    private String description;

    /**
     * 宠物种类名（冗余字段）
     */
    @TableField
    private String speciesName;

    /**
     * 品种图片
     */
    @TableField
    private String imageUrl;

    /**
     * 非数据库字段：用于接收前端传递的关联标签ID列表
     */
    @TableField(exist = false)
    private List<String> petTagIdList;

}