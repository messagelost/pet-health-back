package com.jacob.service.petConfig.impl;

import com.jacob.common.model.petConfig.entity.PetHealthIndicator;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petConfig.PetHealthIndicatorDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petConfig.PetHealthIndicatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetHealthIndicatorServiceImpl extends BaseServiceImpl<PetHealthIndicatorDao, PetHealthIndicator> implements PetHealthIndicatorService {

    @Autowired
    private PetHealthIndicatorDao petHealthIndicatorDao;

    @Override
    public SqlDao getDao() {
        return petHealthIndicatorDao;
    }
}
