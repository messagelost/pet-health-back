package com.jacob.common.model.base;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * 分页结果
 * @param <T>
 */
@Data
public class PageResult<T> {


    private long total; // 总条数
    private int pageNum; // 当前页
    private int pageSize; // 每页大小
    private int pages; // 总页数
    private List<T> list; // 数据列表


    public PageResult() {}


    public PageResult(PageInfo<T> pageInfo) {
        this.total = pageInfo.getTotal();
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.pages = pageInfo.getPages();
        this.list = pageInfo.getList();
    }


// getter / setter
}
