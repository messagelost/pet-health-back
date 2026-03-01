package com.jacob.dao.mappers.author;

import com.jacob.common.model.author.entity.SysUserRole;
import com.jacob.dao.base.SqlDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRoleDao extends SqlDao<SysUserRole> {
    List<SysUserRole> listUserRolesByUserId(String userId);
}
