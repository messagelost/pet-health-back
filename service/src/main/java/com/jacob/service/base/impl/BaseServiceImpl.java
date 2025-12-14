package com.jacob.service.base.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jacob.common.model.base.PageResult;
import com.jacob.dao.base.BaseDao;
import com.jacob.service.base.BaseService;

import java.util.List;

/**
 * 抽象业务层实现类
 * @param <T>
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {


    protected abstract BaseDao<T> getBaseDao();


    @Override
    public boolean save(T entity) {
        return getBaseDao().insert(entity) > 0;
    }


    @Override
    public boolean removeById(String id) {
        return getBaseDao().deleteById(id) > 0;
    }


    @Override
    public boolean updateById(T entity) {
        return getBaseDao().updateById(entity) > 0;
    }


    @Override
    public T getById(String id) {
        return getBaseDao().selectById(id);
    }


    @Override
    public List<T> list() {
        return getBaseDao().selectAll();
    }


    @Override
    public PageResult<T> page(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> list = getBaseDao().selectAll();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo);
    }
}
