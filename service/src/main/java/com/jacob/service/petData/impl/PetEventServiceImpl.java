package com.jacob.service.petData.impl;

import com.jacob.common.model.petData.entity.PetEvent;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petData.PetEventDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petData.PetEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetEventServiceImpl extends BaseServiceImpl<PetEventDao, PetEvent> implements PetEventService {

    @Autowired
    private PetEventDao petEventDao;

    @Override
    public SqlDao getDao() {
        return petEventDao;
    }
}
