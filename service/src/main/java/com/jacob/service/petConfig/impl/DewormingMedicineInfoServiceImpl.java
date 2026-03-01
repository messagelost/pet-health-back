package com.jacob.service.petConfig.impl;

import com.jacob.common.model.petConfig.entity.DewormingMedicineInfo;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petConfig.DewormingMedicineInfoDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petConfig.DewormingMedicineInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DewormingMedicineInfoServiceImpl extends BaseServiceImpl<DewormingMedicineInfoDao, DewormingMedicineInfo> implements DewormingMedicineInfoService {

    @Autowired
    private DewormingMedicineInfoDao dewormingMedicineInfoDao;

    @Override
    public SqlDao getDao() {
        return dewormingMedicineInfoDao;
    }
}
