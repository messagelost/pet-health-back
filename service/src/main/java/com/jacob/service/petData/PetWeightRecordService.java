package com.jacob.service.petData;

import com.jacob.common.model.petData.entity.PetWeightRecord;
import com.jacob.service.base.BaseService;

import java.math.BigDecimal;

public interface PetWeightRecordService extends BaseService<PetWeightRecord> {

    /**
     * 计算宠物 DER
     * @param petId 宠物ID
     * @param coefficient 宠物活动系数
     * @return
     */
    BigDecimal getDer(String petId, BigDecimal coefficient);
}
