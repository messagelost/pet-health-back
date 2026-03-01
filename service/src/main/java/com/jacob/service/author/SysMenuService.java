package com.jacob.service.author;

import aj.org.objectweb.asm.commons.Remapper;
import com.jacob.common.model.author.entity.SysMenu;
import com.jacob.common.model.base.PageResult;
import com.jacob.service.base.BaseService;

import java.util.List;
import java.util.Map;

public interface SysMenuService extends BaseService<SysMenu> {
    List<SysMenu> listMenuByUserId(String userId);

    void registerMenu(String code, String name);
}
