package com.jacob.common.model.petData.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 宠物驱虫记录实体类
 * 对应数据库表：pet_deworming_record
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_deworming_record")
public class PetDewormingRecord extends BaseEntity {

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
     * 药物ID（关联驱虫药物字典表的medicine_id）
     */
    @TableField
    private String medicineId;

    /**
     * 驱虫类型
     */
    @TableField
    private Integer dewormingType;

    /**
     * 实际驱虫日期
     */
    @TableField
    private LocalDate dewormingDate;

    /**
     * 用药剂量
     */
    @TableField
    private Integer dosage;

    /**
     * 剂量单位
     */
    @TableField
    private Integer dosageUnit;

    /**
     * 医院名
     */
    @TableField
    private String hospitalName;

    /**
     * 兽医名
     */
    @TableField
    private String vetName;

    /**
     * 总花费（单位：元，保留2位小数）
     */
    @TableField
    private BigDecimal totalCost;

    /**
     * 不良反应
     */
    @TableField
    private Integer reactionStatus;

    /**
     * 备注
     */
    @TableField
    private String remark;

}