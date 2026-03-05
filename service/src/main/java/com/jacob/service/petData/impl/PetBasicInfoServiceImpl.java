package com.jacob.service.petData.impl;

import com.jacob.common.model.petData.entity.PetBasicInfo;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petData.PetBasicInfoDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petData.PetBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetBasicInfoServiceImpl extends BaseServiceImpl<PetBasicInfoDao, PetBasicInfo> implements PetBasicInfoService {

    @Autowired
    private PetBasicInfoDao petBasicInfoDao;

    @Override
    public SqlDao getDao() {
        return petBasicInfoDao;
    }
}
