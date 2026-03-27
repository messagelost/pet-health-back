package com.jacob.service.petConfig.impl;

import com.jacob.common.model.petConfig.entity.PetActivityCoefficient;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petConfig.PetActivityCoefficientDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petConfig.PetActivityCoefficientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class PetActivityCoefficientServiceImpl extends BaseServiceImpl<PetActivityCoefficientDao, PetActivityCoefficient> implements PetActivityCoefficientService {

    @Autowired
    private PetActivityCoefficientDao petActivityCoefficientDao;

    @Override
    public SqlDao getDao() {
        return petActivityCoefficientDao;
    }

    @Override
    public BigDecimal getPetActivityCoefficient(String petId, String breedId) {
        List<PetActivityCoefficient> list = petActivityCoefficientDao.selectByBreedId(breedId);
        for (PetActivityCoefficient coefficient : list) {
            String caseSql = coefficient.getCaseSql();
            if(caseSql != null){
                caseSql = caseSql.replace("#{petId}", "'" + petId + "'");
                int flag = petActivityCoefficientDao.getByCaseSql(caseSql);
                if(flag > 0){
                    log.info("宠物{}活动系数{}", petId, coefficient.getValue());
                    return coefficient.getValue();
                }
            }
        }
        return BigDecimal.ZERO;
    }

    @Override
    public boolean isNutrition(String text) {
        return text.contains("蛋白")
                || text.contains("脂肪")
                || text.contains("钙")
                || text.contains("磷")
                || text.contains("赖氨酸");
    }
}
