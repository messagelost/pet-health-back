package com.jacob.common.model.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 基础实体类
 */
@Data
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    @TableField
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField
    private LocalDateTime updateTime;

    /**
     * 创建人id
     */
    @TableField
    private String createUserId;

    /**
     * 修改人id
     */
    @TableField
    private String updateUserId;

    /**
     * 创建人
     */
    @TableField(exist = false)
    private String createUserName;

    /**
     * 修改人
     */
    @TableField(exist = false)
    private String updateUserName;


}
