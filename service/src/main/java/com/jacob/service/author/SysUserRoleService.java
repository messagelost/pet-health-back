package com.jacob.service.author;

import com.jacob.common.model.author.entity.SysRole;
import com.jacob.common.model.author.entity.SysUserRole;
import com.jacob.service.base.BaseService;

import java.util.List;

public interface SysUserRoleService extends BaseService<SysUserRole> {
    List<SysUserRole> listUserRoles(String userId);

    List<SysUserRole> listUserRolesInCache(String userId);

    void clearCache();
}
