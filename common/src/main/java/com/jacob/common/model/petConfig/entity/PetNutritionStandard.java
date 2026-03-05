package com.jacob.common.model.petConfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 宠物营养标准参考实体类
 * 对应数据库表：pet_nutrition_standard
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_nutrition_standard")
public class PetNutritionStandard extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 标准ID（主键）
     */
    @TableId
    private String standardId;

    /**
     * 适用宠物品种ID
     */
    @TableField
    private String breedIds;

    /**
     * 生命周期阶段
     */
    @TableField
    private String lifeStage;

    /**
     * 营养成分ID
     */
    @TableField
    private String configId;

    /**
     * 上限值
     */
    @TableField
    private BigDecimal upperLimit;

    /**
     * 下限值
     */
    @TableField
    private BigDecimal lowerLimit;

    /**
     * 推荐值
     */
    @TableField
    private BigDecimal recommend;


    @TableField(exist = false)
    private String lifeStageName;

}
