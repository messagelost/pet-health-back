package com.jacob.dao.base;

import java.util.List;

/**
 * 基础Dao
 * @param <T>
 */
public interface BaseDao<T> {


    int insert(T entity);


    int deleteById(String id);


    int updateById(T entity);


    T selectById(String id);


    List<T> selectAll();
}