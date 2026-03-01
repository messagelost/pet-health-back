package com.jacob.service.petConfig.impl;

import com.jacob.common.model.petConfig.entity.PetActivityCoefficient;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petConfig.PetActivityCoefficientDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petConfig.PetActivityCoefficientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetActivityCoefficientServiceImpl extends BaseServiceImpl<PetActivityCoefficientDao, PetActivityCoefficient> implements PetActivityCoefficientService {

    @Autowired
    private PetActivityCoefficientDao petActivityCoefficientDao;

    @Override
    public SqlDao getDao() {
        return petActivityCoefficientDao;
    }
}
