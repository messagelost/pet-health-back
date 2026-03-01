package com.jacob.dao.mappers.author;

import com.jacob.common.model.author.entity.SysMenu;
import com.jacob.dao.base.SqlDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuDao extends SqlDao<SysMenu> {

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuByUserId(String userId);
}
