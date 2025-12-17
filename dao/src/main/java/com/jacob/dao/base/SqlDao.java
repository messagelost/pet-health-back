package com.jacob.dao.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface SqlDao<T> extends BaseMapper<T> {


    /**
     * 新增
     */
    <T> int insertWithBean(T bean);

    /**
     * 更新
     */
    <T> int updateWithBean(T bean);

    /**
     * 删除主键对应的记录
     */
    <T> int deleteByPrimaryKey(String id);

    /**
     * 批量删除主键对应的记录
     */
    <T> int deleteBatchByPrimaryKeys(String[] id);

    /**
     * 根据条件查询唯一的记录
     */
    <T> T selectOneById(String id);

    /**
     * 根据条件查询所有符合条件记录
     */
    <T> List<T> selectAllList(Map<String, Object> params);

    /**
     * 查询总数
     */
    <T> int getCount(Map<String, Object>  params);

    /**
     * 导出数据
     */
    List<Map<String, Object>> exportData(Map<String, Object> params);

}
