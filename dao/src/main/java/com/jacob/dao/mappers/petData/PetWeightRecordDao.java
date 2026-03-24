package com.jacob.dao.mappers.petData;

import com.jacob.common.model.petData.entity.PetWeightRecord;
import com.jacob.dao.base.SqlDao;
import org.springframework.stereotype.Repository;

@Repository
public interface PetWeightRecordDao extends SqlDao<PetWeightRecord> {
    PetWeightRecord selectLatestByPetId(String petId);
}
