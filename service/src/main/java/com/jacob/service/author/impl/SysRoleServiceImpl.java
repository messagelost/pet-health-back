package com.jacob.service.author.impl;

import com.jacob.common.model.author.entity.SysRole;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.author.SysRoleDao;
import com.jacob.service.author.SysRoleService;
import com.jacob.service.base.impl.BaseServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public SqlDao getDao() {
        return sysRoleDao;
    }
}
