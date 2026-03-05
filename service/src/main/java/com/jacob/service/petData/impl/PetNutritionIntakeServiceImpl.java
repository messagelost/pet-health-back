package com.jacob.service.petData.impl;

import com.jacob.common.model.petData.entity.PetNutritionIntake;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petData.PetNutritionIntakeDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petData.PetNutritionIntakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetNutritionIntakeServiceImpl extends BaseServiceImpl<PetNutritionIntakeDao, PetNutritionIntake> implements PetNutritionIntakeService {

    @Autowired
    private PetNutritionIntakeDao petNutritionIntakeDao;

    @Override
    public SqlDao getDao() {
        return petNutritionIntakeDao;
    }
}
