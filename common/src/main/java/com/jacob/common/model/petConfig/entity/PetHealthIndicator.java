package com.jacob.common.model.petConfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 宠物健康指标定义实体类
 * 对应数据库表：pet_health_indicator
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_health_indicator")
public class PetHealthIndicator extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 指标ID（主键）
     */
    @TableId
    private String indicatorId;

    /**
     * 指标名称
     */
    @TableField
    private String indicatorName;

    /**
     * 单位
     */
    @TableField
    private String unit;

    /**
     * 指标描述
     */
    @TableField
    private String description;

    /**
     * 输入类型:number / text / enum
     */
    @TableField
    private String inputType;

}