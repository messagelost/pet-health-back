package com.jacob.service.petData.impl;

import com.jacob.common.model.petData.entity.PetDewormingRecord;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petData.PetDewormingRecordDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petData.PetDewormingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetDewormingRecordServiceImpl extends BaseServiceImpl<PetDewormingRecordDao, PetDewormingRecord> implements PetDewormingRecordService {

    @Autowired
    private PetDewormingRecordDao petDewormingRecordDao;

    @Override
    public SqlDao getDao() {
        return petDewormingRecordDao;
    }
}
