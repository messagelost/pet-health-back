package com.jacob.dao.mappers.author;

import com.jacob.common.model.author.entity.SysMenu;
import com.jacob.common.model.author.entity.SysMenuRole;
import com.jacob.dao.base.SqlDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuRoleDao extends SqlDao<SysMenuRole> {
    List<SysMenu> selectMenuByRoleId(String roleId);
}
