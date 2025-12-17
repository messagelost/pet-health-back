package com.jacob.service.user.userInfo.impl;

import com.jacob.common.model.user.entity.SysUserInfo;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.user.SysUserInfoDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.user.userInfo.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserInfoServiceImpl extends BaseServiceImpl<SysUserInfoDao,SysUserInfo> implements SysUserInfoService {

    @Autowired
    private SysUserInfoDao sysUserInfoDao;

    @Override
    public SqlDao getDao() {
        return sysUserInfoDao;
    }
}
