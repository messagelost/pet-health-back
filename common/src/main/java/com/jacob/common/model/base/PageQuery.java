package com.jacob.common.model.base;


import lombok.Data;

/**
 * 分页查询参数
 */
@Data
public class PageQuery {

    /**
     * 当前页，默认第 1 页
     */
    private Integer pageNum = 1;

    /**
     * 每页大小，默认 10 条
     */
    private Integer pageSize = 10;
}
