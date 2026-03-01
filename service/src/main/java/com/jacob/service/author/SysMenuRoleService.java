package com.jacob.service.author;

import com.jacob.common.model.author.entity.SysMenu;
import com.jacob.common.model.author.entity.SysMenuRole;
import com.jacob.service.base.BaseService;

import java.util.List;

public interface SysMenuRoleService extends BaseService<SysMenuRole> {
    List<SysMenu> selectMenuByRoleId(String roleId);

    void saveOrUpdateList(String roleId, List<String> menuIdList);
}
