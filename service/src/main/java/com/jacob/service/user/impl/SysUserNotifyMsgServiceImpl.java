package com.jacob.service.user.impl;

import com.jacob.common.model.user.entity.SysUserNotifyMsg;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.user.SysUserNotifyMsgDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.user.SysUserNotifyMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserNotifyMsgServiceImpl extends BaseServiceImpl<SysUserNotifyMsgDao, SysUserNotifyMsg> implements SysUserNotifyMsgService {

    @Autowired
    private SysUserNotifyMsgDao sysUserNotifyMsgDao;

    @Override
    public SqlDao getDao() {
        return sysUserNotifyMsgDao;
    }
}
