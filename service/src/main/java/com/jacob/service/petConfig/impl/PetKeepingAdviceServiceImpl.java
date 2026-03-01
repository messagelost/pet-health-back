package com.jacob.service.petConfig.impl;

import com.jacob.common.model.petConfig.entity.PetKeepingAdvice;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petConfig.PetKeepingAdviceDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petConfig.PetKeepingAdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetKeepingAdviceServiceImpl extends BaseServiceImpl<PetKeepingAdviceDao, PetKeepingAdvice> implements PetKeepingAdviceService {

    @Autowired
    private PetKeepingAdviceDao petKeepingAdviceDao;

    @Override
    public SqlDao getDao() {
        return petKeepingAdviceDao;
    }
}
