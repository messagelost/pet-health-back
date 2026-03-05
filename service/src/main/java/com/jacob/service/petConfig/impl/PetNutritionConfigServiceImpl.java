package com.jacob.service.petConfig.impl;

import com.jacob.common.model.petConfig.entity.PetNutritionConfig;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petConfig.PetNutritionConfigDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petConfig.PetNutritionConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetNutritionConfigServiceImpl extends BaseServiceImpl<PetNutritionConfigDao, PetNutritionConfig> implements PetNutritionConfigService {

    @Autowired
    private PetNutritionConfigDao petNutritionConfigDao;

    @Override
    public SqlDao getDao() {
        return petNutritionConfigDao;
    }
}
