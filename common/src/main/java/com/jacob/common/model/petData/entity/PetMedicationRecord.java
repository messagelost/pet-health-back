package com.jacob.common.model.petData.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 宠物用药记录实体类
 * 对应数据库表：pet_medication_record
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_medication_record")
public class PetMedicationRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private String recordId;

    /**
     * 宠物ID（关联pet_basic_info表的pet_id）
     */
    @TableField
    private String petId;

    /**
     * 药物名称
     */
    @TableField
    private String medicationName;

    /**
     * 药物类型
     */
    @TableField
    private Integer medicationType;

    /**
     * 实际用药剂量（如：2片/次、5ml/天）
     */
    @TableField
    private String dosage;

    /**
     * 推荐剂量（如：1-2片/次、3-5ml/天）
     */
    @TableField
    private String recommendedDosage;

    /**
     * 用药途径
     */
    @TableField
    private Integer route;

    /**
     * 用药开始时间
     */
    @TableField
    private LocalDate startDate;

    /**
     * 供药方（医院/药店名称）
     */
    @TableField
    private String prescriber;

    /**
     * 备注
     */
    @TableField
    private String remark;

}