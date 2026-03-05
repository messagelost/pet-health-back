package com.jacob.service.petData.impl;

import com.jacob.common.model.petData.entity.PetMedicationRecord;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petData.PetMedicationRecordDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petData.PetMedicationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetMedicationRecordServiceImpl extends BaseServiceImpl<PetMedicationRecordDao, PetMedicationRecord> implements PetMedicationRecordService {

    @Autowired
    private PetMedicationRecordDao petMedicationRecordDao;

    @Override
    public SqlDao getDao() {
        return petMedicationRecordDao;
    }
}
