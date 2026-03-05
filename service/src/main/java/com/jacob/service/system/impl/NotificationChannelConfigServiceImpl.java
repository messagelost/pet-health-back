package com.jacob.service.system.impl;

import com.jacob.common.model.system.entity.NotificationChannelConfig;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.system.NotificationChannelConfigDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.system.NotificationChannelConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationChannelConfigServiceImpl extends BaseServiceImpl<NotificationChannelConfigDao, NotificationChannelConfig> implements NotificationChannelConfigService {

    @Autowired
    private NotificationChannelConfigDao notificationChannelConfigDao;

    @Override
    public SqlDao getDao() {
        return notificationChannelConfigDao;
    }
}
