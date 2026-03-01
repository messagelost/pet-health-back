package com.jacob.service.author.impl;

import com.jacob.common.model.author.entity.SysRole;
import com.jacob.common.model.author.entity.SysUserRole;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.author.SysUserRoleDao;
import com.jacob.service.author.SysUserRoleService;
import com.jacob.service.base.impl.BaseServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleDao, SysUserRole> implements SysUserRoleService {

    @Resource
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public SqlDao getDao() {
        return sysUserRoleDao;
    }

    @Override
    public List<SysUserRole> listUserRoles(String userId) {
        return sysUserRoleDao.listUserRolesByUserId(userId);
    }

}
