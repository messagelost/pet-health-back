package com.jacob.service.system.impl;

import com.jacob.common.model.system.entity.NotificationLog;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.system.NotificationLogDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.system.NotificationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationLogServiceImpl extends BaseServiceImpl<NotificationLogDao, NotificationLog> implements NotificationLogService {

    @Autowired
    private NotificationLogDao notificationLogDao;

    @Override
    public SqlDao getDao() {
        return null;
    }
}
