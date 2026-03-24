package com.jacob.service.petData.impl;

import com.jacob.common.model.petData.entity.PetBasicInfo;
import com.jacob.common.model.petData.entity.PetWeightRecord;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petData.PetBasicInfoDao;
import com.jacob.dao.mappers.petData.PetWeightRecordDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petData.PetBasicInfoService;
import com.jacob.service.petData.PetWeightRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class PetWeightRecordServiceImpl extends BaseServiceImpl<PetWeightRecordDao, PetWeightRecord> implements PetWeightRecordService {

    @Autowired
    private PetWeightRecordDao petWeightRecordDao;

    @Override
    public SqlDao getDao() {
        return petWeightRecordDao;
    }

    @Override
    public BigDecimal getDer(String petId, BigDecimal coefficient) {
        PetWeightRecord last = petWeightRecordDao.selectLatestByPetId(petId);
        if(last != null){
            BigDecimal weight = last.getWeight();
            BigDecimal rer = BigDecimal.valueOf(70).multiply(
                    BigDecimal.valueOf(Math.pow(weight.doubleValue(), 0.75))
            );
            log.info("宠物{}的DER{}", petId, rer.multiply(coefficient));
            return rer.multiply(coefficient);
        }
        return BigDecimal.ZERO;
    }
}
