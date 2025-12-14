package com.jacob.service.user.userInfo.impl;

import com.jacob.common.model.user.entity.SysUserInfo;
import com.jacob.dao.base.BaseDao;
import com.jacob.dao.user.SysUserInfoDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.user.userInfo.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息服务实现类
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserInfo> implements SysUserInfoService{

    @Autowired
    private SysUserInfoDao sysUserInfoDao;


    @Override
    protected BaseDao<SysUserInfo> getBaseDao() {
        return sysUserInfoDao;
    }
}
