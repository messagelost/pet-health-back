package com.jacob.service.petConfig.impl;

import com.jacob.common.model.petConfig.entity.PetNutritionStandard;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petConfig.PetNutritionStandardDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petConfig.PetNutritionStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetNutritionStandardServiceImpl extends BaseServiceImpl<PetNutritionStandardDao, PetNutritionStandard> implements PetNutritionStandardService {

    @Autowired
    private PetNutritionStandardDao petNutritionStandardDao;

    @Override
    public SqlDao getDao() {
        return petNutritionStandardDao;
    }
}
