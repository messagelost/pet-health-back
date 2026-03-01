package com.jacob.service.petConfig.impl;

import com.jacob.common.model.petConfig.entity.VaccineInfo;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petConfig.VaccineInfoDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petConfig.VaccineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccineInfoServiceImpl extends BaseServiceImpl<VaccineInfoDao, VaccineInfo> implements VaccineInfoService {

    @Autowired
    private VaccineInfoDao vaccineInfoDao;
    
    @Override
    public SqlDao getDao() {
        return vaccineInfoDao;
    }
}
