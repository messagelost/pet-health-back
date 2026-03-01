package com.jacob.service.petConfig.impl;

import com.jacob.common.model.petConfig.entity.GroomingTypeInfo;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petConfig.GroomingTypeInfoDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petConfig.GroomingTypeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GroomingTypeInfoServiceImpl extends BaseServiceImpl<GroomingTypeInfoDao, GroomingTypeInfo> implements GroomingTypeInfoService {

    @Autowired
    private GroomingTypeInfoDao groomingTypeInfoDao;

    @Override
    public SqlDao getDao() {
        return groomingTypeInfoDao;
    }
}
