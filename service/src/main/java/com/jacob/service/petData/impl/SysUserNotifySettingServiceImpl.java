package com.jacob.service.petData.impl;

import com.jacob.common.model.user.entity.SysUserNotifySetting;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.user.SysUserNotifySettingDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petData.SysUserNotifySettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserNotifySettingServiceImpl extends BaseServiceImpl<SysUserNotifySettingDao, SysUserNotifySetting> implements SysUserNotifySettingService {

    @Autowired
    private SysUserNotifySettingDao sysUserNotifySettingDao;

    @Override
    public SqlDao getDao() {
        return null;
    }
}
