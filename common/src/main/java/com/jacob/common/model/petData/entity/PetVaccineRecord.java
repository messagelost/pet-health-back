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
 * 宠物疫苗记录实体类
 * 对应数据库表：pet_vaccine_record
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_vaccine_record")
public class PetVaccineRecord extends BaseEntity {

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
     * 品种ID（关联pet_breed表的breed_id）
     */
    @TableField
    private String breedId;

    /**
     * 疫苗ID（关联疫苗字典表的vaccine_id）
     */
    @TableField
    private String vaccineId;

    /**
     * 接种时间
     */
    @TableField
    private LocalDate injectionDate;

    /**
     * 接种部位
     */
    @TableField
    private Integer injectionSite;

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