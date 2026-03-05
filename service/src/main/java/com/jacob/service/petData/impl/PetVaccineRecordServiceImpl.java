package com.jacob.service.petData.impl;

import com.jacob.common.model.petData.entity.PetVaccineRecord;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petData.PetVaccineRecordDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petData.PetVaccineRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetVaccineRecordServiceImpl extends BaseServiceImpl<PetVaccineRecordDao, PetVaccineRecord> implements PetVaccineRecordService {

    @Autowired
    private PetVaccineRecordDao petVaccineRecordDao;

    @Override
    public SqlDao getDao() {
        return petVaccineRecordDao;
    }
}
