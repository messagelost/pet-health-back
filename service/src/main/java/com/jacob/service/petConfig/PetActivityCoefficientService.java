package com.jacob.service.petConfig;

import com.jacob.common.model.petConfig.entity.PetActivityCoefficient;
import com.jacob.service.base.BaseService;

import java.math.BigDecimal;

public interface PetActivityCoefficientService extends BaseService<PetActivityCoefficient> {
    BigDecimal getPetActivityCoefficient(String petId, String breedId);

    boolean isNutrition(String name);
}
