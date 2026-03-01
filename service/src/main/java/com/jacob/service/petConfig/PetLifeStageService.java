package com.jacob.service.petConfig;

import com.jacob.common.model.petConfig.entity.PetLifeStage;
import com.jacob.service.base.BaseService;

import java.util.List;

public interface PetLifeStageService extends BaseService<PetLifeStage> {
    void saveOrUpdateStage(String speciesId, List<PetLifeStage> lifeStageList);
}
