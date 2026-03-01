package com.jacob.common.model.petConfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 洗护类型信息实体类
 * 对应数据库表：grooming_type_info
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("grooming_type_info")
public class GroomingTypeInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 类型ID（主键）
     */
    @TableId
    private String typeId;

    /**
     * 类型名称
     */
    @TableField
    private String groomingType;

    /**
     * 适用宠物品种ID（多个用逗号分隔）
     */
    @TableField
    private String breedIds;

    /**
     * 描述
     */
    @TableField
    private String description;

    /**
     * 推荐间隔天数
     */
    @TableField
    private Integer cycleDays;

    /**
     * 非数据库字段：适用品种ID列表（前端传递时拆分用）
     */
    @TableField(exist = false)
    private List<String> breedIdList;

}