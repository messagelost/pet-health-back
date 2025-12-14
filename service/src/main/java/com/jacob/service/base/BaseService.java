package com.jacob.service.base;

import com.jacob.common.model.base.PageResult;

import java.util.List;

/**
 * 基础服务类
 * @param <T>
 */
public interface BaseService<T> {


    boolean save(T entity);


    boolean removeById(String id);


    boolean updateById(T entity);


    T getById(String id);


    List<T> list();


    PageResult<T> page(int pageNum, int pageSize);
}