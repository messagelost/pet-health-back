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
 * 宠物洗护记录实体类
 * 对应数据库表：pet_grooming_record
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_grooming_record")
public class PetGroomingRecord extends BaseEntity {

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
     * 洗护类型ID（关联洗护类型字典表的type_id）
     */
    @TableField
    private String typeId;

    /**
     * 洗护时间
     */
    @TableField
    private LocalDate groomingDate;

    /**
     * 服务提供类型（1=宠物店，2=上门服务，3=医院，4=其他）
     */
    @TableField
    private Integer serviceProviderType;

    /**
     * 服务提供方名称
     */
    @TableField
    private String serviceProviderName;

    /**
     * 总花费（单位：元，保留2位小数）
     */
    @TableField
    private BigDecimal totalCost;

    /**
     * 护理前图片URL（多个URL用逗号分隔）
     */
    @TableField
    private String photosBefore;

    /**
     * 护理后图片URL（多个URL用逗号分隔）
     */
    @TableField
    private String photosAfter;

    /**
     * 备注
     */
    @TableField
    private String remark;

}