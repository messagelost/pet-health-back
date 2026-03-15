package com.jacob.service.petData.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.model.user.entity.SysUserNotifySetting;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.user.SysUserNotifySettingDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petData.SysUserNotifySettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserNotifySettingServiceImpl extends BaseServiceImpl<SysUserNotifySettingDao, SysUserNotifySetting> implements SysUserNotifySettingService {

    @Autowired
    private SysUserNotifySettingDao sysUserNotifySettingDao;

    @Override
    public SqlDao getDao() {
        return null;
    }

    @Override
    public List<String> getConfigChannel(String userId) {
        // 查询用户有几种提醒方式
        return list(
                    new LambdaQueryWrapper<SysUserNotifySetting>()
                        .eq(SysUserNotifySetting::getUserId, userId)
                        .eq(SysUserNotifySetting::getEnabled, 1)
                ).stream().map(SysUserNotifySetting::getChannel).filter(s -> s != null && !s.isEmpty()).toList();

    }
}
