package com.jacob.service.petData.impl;

import com.jacob.common.model.petData.entity.PetGroomingRecord;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petData.PetGroomingRecordDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petData.PetGroomingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetGroomingRecordServiceImpl extends BaseServiceImpl<PetGroomingRecordDao, PetGroomingRecord> implements PetGroomingRecordService {

    @Autowired
    private PetGroomingRecordDao petGroomingRecordDao;

    @Override
    public SqlDao getDao() {
        return petGroomingRecordDao;
    }
}
