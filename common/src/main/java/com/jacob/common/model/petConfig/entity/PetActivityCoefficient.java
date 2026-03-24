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
 * 宠物活动系数参考实体类
 * 对应数据库表：pet_activity_coefficient
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_activity_coefficient")
public class PetActivityCoefficient extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 系数ID（主键）
     */
    @TableId
    private String coefficientId;

    /**
     * 适用宠物品种ID（多个用逗号分隔）
     */
    @TableField
    private String breedIds;

    /**
     * 计算优先级（数值越小优先级越高）
     */
    @TableField
    private Integer calculatePriority;

    /**
     * 系数值
     */
    @TableField
    private BigDecimal value;

    /**
     * 适用情况
     */
    @TableField
    private String caseSql;

    /**
     * 适用情况描述
     */
    @TableField
    private String caseDesc;

    /**
     * 非数据库字段：适用品种ID列表（前端传递时拆分用）
     */
    @TableField(exist = false)
    private List<String> breedIdList;

}