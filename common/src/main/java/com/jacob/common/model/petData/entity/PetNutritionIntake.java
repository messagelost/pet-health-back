package com.jacob.common.model.petData.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 宠物营养摄入实体类
 * 对应数据库表：pet_nutrition_intake
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_nutrition_intake")
public class PetNutritionIntake extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String intakeId;

    /**
     * 宠物ID（关联pet_basic_info表的pet_id）
     */
    @TableField
    private String petId;

    /**
     * 摄入日期
     */
    @TableField
    private LocalDate intakeDate;

    /**
     * 食物类型（1=干粮，2=湿粮，3=自制粮，4=零食，5=营养补充剂，6=其他）
     */
    @TableField
    private Integer foodType;

    /**
     * 营养成分JSON（{"营养成分ID":25.5}）
     */
    @TableField
    private String nutritionJson;

    /**
     * 备注
     */
    @TableField
    private String remark;


}