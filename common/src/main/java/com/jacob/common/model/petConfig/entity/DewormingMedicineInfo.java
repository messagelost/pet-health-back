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
 * 驱虫药物信息实体类
 * 对应数据库表：deworming_medicine_info
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("deworming_medicine_info")
public class DewormingMedicineInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 药物ID（主键）
     */
    @TableId
    private String medicineId;

    /**
     * 药物名称
     */
    @TableField
    private String medicineName;

    /**
     * 品牌
     */
    @TableField
    private String brand;

    /**
     * 类型（整型：1=体内，2=体外）
     */
    @TableField
    private Integer type;

    /**
     * 驱除寄生虫
     */
    @TableField
    private String targetParasites;

    /**
     * 价格
     */
    @TableField
    private BigDecimal cost;

    /**
     * 适用宠物品种ID（多个用逗号分隔）
     */
    @TableField
    private String breedIds;

    /**
     * 有效期
     */
    @TableField
    private Integer validityPeriod;

    /**
     * 有效期单位（整型：1=天，2=月，3=年）
     */
    @TableField
    private Integer periodUnit;

    /**
     * 使用方法（涂抹/口服/注射）
     */
    @TableField
    private String usageMethod;

    /**
     * 药物说明
     */
    @TableField
    private String description;

    /**
     * 适用次数
     */
    @TableField
    private Integer times;

    /**
     * 非数据库字段：适用品种ID列表（前端传递时拆分用）
     */
    @TableField(exist = false)
    private List<String> breedIdList;

    /**
     * 非数据库字段：有效期单位选项ID列表
     */
    @TableField(exist = false)
    private List<String> periodUnitOptionIdList;

}