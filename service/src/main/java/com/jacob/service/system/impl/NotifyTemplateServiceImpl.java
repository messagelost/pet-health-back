package com.jacob.service.system.impl;

import com.jacob.common.model.system.entity.NotifyTemplate;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.system.NotifyTemplateDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.system.NotifyTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyTemplateServiceImpl extends BaseServiceImpl<NotifyTemplateDao, NotifyTemplate> implements NotifyTemplateService {

    @Autowired
    private NotifyTemplateDao notifyTemplateDao;

    @Override
    public SqlDao getDao() {
        return notifyTemplateDao;
    }
}
