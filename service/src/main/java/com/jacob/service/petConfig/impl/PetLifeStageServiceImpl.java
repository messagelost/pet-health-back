package com.jacob.service.petConfig.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.model.petConfig.entity.PetLifeStage;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petConfig.PetLifeStageDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petConfig.PetLifeStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetLifeStageServiceImpl extends BaseServiceImpl<PetLifeStageDao, PetLifeStage> implements PetLifeStageService {

    @Autowired
    private PetLifeStageDao petLifeStageDao;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public SqlDao getDao() {
        return petLifeStageDao;
    }

    @Override
    public void saveOrUpdateStage(String speciesId, List<PetLifeStage> lifeStageList) {
        List<String> existList = new ArrayList<>(list(new LambdaQueryWrapper<PetLifeStage>().eq(PetLifeStage::getSpeciesId, speciesId)).stream().map(PetLifeStage::getStageId).toList());

        for (int i = 0; i < lifeStageList.size(); i++) {
            PetLifeStage lifeStage = lifeStageList.get(i);
            lifeStage.setSort(i); // 根据索引设置排序值

            if (lifeStage.getStageId() == null) {
                lifeStage.setStageId(snowflakeIdGenerator.generateIdWithPrefix("L"));
                lifeStage.setSpeciesId(speciesId);
                petLifeStageDao.insertWithBean(lifeStage);
            } else {
                lifeStage.setSpeciesId(speciesId);
                petLifeStageDao.updateWithBean(lifeStage);
                existList.remove(lifeStage.getStageId());
            }
        }

        existList.forEach(stageId -> petLifeStageDao.deleteById(stageId));
    }
}
