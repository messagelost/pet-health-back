package com.jacob.dao.mappers.petData;

import com.jacob.common.model.petData.entity.PetEvent;
import com.jacob.dao.base.SqlDao;
import org.springframework.stereotype.Repository;

@Repository
public interface PetEventDao extends SqlDao<PetEvent> {
}
