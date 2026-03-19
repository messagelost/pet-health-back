package com.jacob.common.model.petConfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 宠物健康指标适用规则实体类
 * 对应数据库表：pet_health_indicator_rule
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_health_indicator_rule")
public class PetHealthIndicatorRule extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 规则ID（主键）
     */
    @TableId
    private String ruleId;

    /**
     * 品种ID（关联pet_breed表的breed_id）
     */
    @TableField
    private String breedId;

    /**
     * 品种名称
     */
    @TableField(exist = false)
    private String breedName;

    /**
     * 指标ID（关联pet_health_indicator表的indicator_id）
     */
    @TableField
    private String indicatorId;

    /**
     * 指标名称（冗余字段）
     */
    @TableField
    private String indicatorName;

    /**
     * 生命周期阶段
     */
    @TableField
    private String lifeStage;

    /**
     * 生命周期阶段
     */
    @TableField(exist = false)
    private String lifeStageName;

    /**
     * 最小值
     */
    @TableField
    private BigDecimal minValue;

    /**
     * 最大值
     */
    @TableField
    private BigDecimal maxValue;

    /**
     * 单位
     */
    @TableField
    private String unit;

    /**
     * 低于指标备注
     */
    @TableField
    private String belowRemark;

    /**
     * 高于指标备注
     */
    @TableField
    private String aboveRemark;

}