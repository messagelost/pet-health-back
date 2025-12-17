package com.jacob.service.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jacob.common.model.base.PageResult;

import java.util.List;
import java.util.Map;

public interface BaseService<T> extends IService<T> {

    /**
     * 根据id删除
     */
    <T> int deleteById(String id);

    /**
     * 批量根据id删除
     */
    <T> int deleteByIds(String[] id);
    /**
     * 新增记录
     */
    <T> int insertWithBean(T record);

    /**
     * 根据id查询记录
     */
    <T> T findBeanById(String id);

    /**
     * 更新信息
     */
    <T> int updateWithBean(T record);

    /**
     * 根据条件查询所有记录
     */
    <T> List<T> selectAllList(Map<String, Object> params);

    /**
     * 根据条件分页查询记录
     */
    PageResult<T> selectPageList(Map<String, Object> params);

    /**
     * 查询总数
     */
    <T> int getCount(Map<String, Object> params);

    /**
     * 导出数据
     */
    List<Map<String, Object>> exportData(Map<String, Object> params);
}
