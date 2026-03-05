package com.jacob.service.petData.impl;

import com.jacob.common.model.petData.entity.PetBasicInfo;
import com.jacob.common.model.petData.entity.PetWeightRecord;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petData.PetBasicInfoDao;
import com.jacob.dao.mappers.petData.PetWeightRecordDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petData.PetBasicInfoService;
import com.jacob.service.petData.PetWeightRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetWeightRecordServiceImpl extends BaseServiceImpl<PetWeightRecordDao, PetWeightRecord> implements PetWeightRecordService {

    @Autowired
    private PetWeightRecordDao petWeightRecordDao;

    @Override
    public SqlDao getDao() {
        return petWeightRecordDao;
    }
}
