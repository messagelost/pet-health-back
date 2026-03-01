package com.jacob.service.base.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jacob.common.model.base.PageResult;
import com.jacob.dao.base.SqlDao;
import com.jacob.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {


    public abstract SqlDao getDao();


    @Override
    public <T> int deleteById(String serializable) {
        return getDao().deleteByPrimaryKey(serializable);
    }


    @Override
    public <T> int deleteByIds(String[] serializable) {
        return getDao().deleteBatchByPrimaryKeys(serializable);
    }


    @Override
    public <T> int insertWithBean(T record) {
        return getDao().insertWithBean(record);
    }

    @Override
    public <T> T findBeanById(String serializable) {
        return (T) getDao().selectOneById(serializable);
    }

    @Override
    public <T> int updateWithBean(T record) {
        return getDao().updateWithBean(record);
    }

    @Override
    public <T> List<T> selectAllList(Map<String, Object> params) {
        return getDao().selectAllList(params);
    }

    @Override
    public PageResult<T> selectPageList(Map<String, Object> params) {
        //分页处理
        PageHelper.startPage(params);
        List<T> list = getDao().selectAllList(params);
        PageInfo<T> page = new PageInfo<T>(list);
        return new PageResult<T>(page);
    }

    @Override
    public <T> int getCount(Map<String, Object> params) {
        return getDao().getCount(params);
    }

    @Override
    public List<Map<String, Object>> exportData(Map<String, Object> params) {
        return getDao().exportData(params);
    }
}
