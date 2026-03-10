package com.jacob.service.system.impl;

import com.jacob.common.model.system.entity.NotifyLog;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.system.NotifyLogDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.system.NotifyLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyLogServiceImpl extends BaseServiceImpl<NotifyLogDao, NotifyLog> implements NotifyLogService {

    @Autowired
    private NotifyLogDao notifyLogDao;

    @Override
    public SqlDao getDao() {
        return notifyLogDao;
    }
}
