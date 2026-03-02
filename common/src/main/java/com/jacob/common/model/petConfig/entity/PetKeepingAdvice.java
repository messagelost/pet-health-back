package com.jacob.common.model.petConfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 宠物饲养建议实体类
 * 对应数据库表：pet_keeping_advice
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_keeping_advice")
public class PetKeepingAdvice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 建议ID（主键）
     */
    @TableId
    private String adviceId;

    /**
     * 品种ID（关联pet_breed表的breed_id）
     */
    @TableField
    private String breedId;

    /**
     * 生命周期阶段
     */
    @TableField
    private String lifeStage;

    /**
     * 建议类型（整型）
     */
    @TableField
    private Integer adviceType;

    /**
     * 建议内容
     */
    @TableField
    private String adviceContent;

    /**
     * 建议频率
     */
    @TableField
    private String adviceFrequency;

    /**
     * 备注
     */
    @TableField
    private String remark;

    @TableField(exist = false)
    private String breedName;

    @TableField(exist = false)
    private String stageName;

}