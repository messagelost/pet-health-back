package com.jacob.common.model.petConfig.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jacob.common.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 疫苗信息实体类
 * 对应数据库表：vaccine_info
 * @author jacob
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("vaccine_info")
public class VaccineInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 疫苗ID（主键）
     */
    @TableId
    private String vaccineId;

    /**
     * 疫苗名称
     */
    @TableField
    private String vaccineName;

    /**
     * 疫苗简称
     */
    @TableField
    private String vaccineShort;

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
     * 生产厂家
     */
    @TableField
    private String manufacturer;

    /**
     * 有效期
     */
    @TableField
    private Integer validityPeriod;

    /**
     * 有效期单位（整型：如1=天，2=月，3=年）
     */
    @TableField
    private Integer periodUnit;

    /**
     * 接种总次数
     */
    @TableField
    private Integer injectionTimes;

    /**
     * 每一剂间隔天数
     */
    @TableField
    private Integer intervalDays;

    /**
     * 加强免疫间隔天数
     */
    @TableField
    private Integer enhanceDays;

    /**
     * 适用宠物品种
     */
    @TableField(exist = false)
    private String breedNames;

    /**
     * 适用宠物品种
     */
    @TableField(exist = false)
    private String breedId;

    /**
     * 疫苗名称检索
     */
    @TableField(exist = false)
    private String vaccineNameSearch;

    @TableField(exist = false)
    private String suggestType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @TableField(exist = false)
    private LocalDateTime nextInjectionDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @TableField(exist = false)
    private LocalDateTime lastInjectionDate;

}