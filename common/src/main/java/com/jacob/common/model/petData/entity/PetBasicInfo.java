package com.jacob.common.model.petData.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 宠物基础信息实体类
 * 对应数据库表：pet_basic_info
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pet_basic_info")
public class PetBasicInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 宠物ID（主键）
     */
    @TableId
    private String petId;

    /**
     * 品种ID（关联pet_breed表的breed_id）
     */
    @TableField
    private String breedId;

    /**
     * 品种名称
     */
    @TableField(exist = false)
    private String breedName;

    /**
     * 宠物名
     */
    @TableField
    private String petName;

    /**
     * 主人ID
     */
    @TableField
    private String userId;

    /**
     * 宠物生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField
    private LocalDate petBirthday;

    /**
     * 宠物性别（0=雌性，1=雄性）
     */
    @TableField
    private Integer petSex;

    /**
     * 是否绝育（0=否，1=是）
     */
    @TableField
    private Integer neutered;

    /**
     * 是否怀孕（0=否，1=是）
     */
    @TableField
    private Integer pregnant;

    /**
     * 生命周期
     */
    @TableField
    private String lifeStage;

    /**
     * 生命阶段名称
     */
    @TableField(exist = false)
    private String lifeStageName;

    /**
     * 宠物照片
     */
    @TableField
    private String petImgUrl;

    /**
     * 宠物重量
     */
    @TableField(exist = false)
    private BigDecimal petWeight;

    @TableField(exist = false)
    private BigDecimal monthAge;

    @TableField(exist = false)
    private BigDecimal yearAge;

    @TableField(exist = false)
    private String speciesId;


}