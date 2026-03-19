package com.jacob.common.model.petData.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 宠物体重记录实体类
 * 对应数据库表：pet_weight_record
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_weight_record")
public class PetWeightRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 记录ID（主键）
     */
    @TableId
    private String recordId;

    /**
     * 宠物ID（关联pet_basic_info表的pet_id）
     */
    @TableField
    private String petId;

    /**
     * 体重（单位：kg，保留2位小数）
     */
    @TableField
    private BigDecimal weight;

    /**
     * 生命阶段
     */
    @TableField
    private String lifeStage;

    @TableField(exist = false)
    private List<String> petIds;

    @TableField(exist = false)
    private String minValue;

    @TableField(exist = false)
    private String maxValue;

    @TableField(exist = false)
    private String unit;

    @TableField(exist = false)
    private String belowRemark;

    @TableField(exist = false)
    private String aboveRemark;

}