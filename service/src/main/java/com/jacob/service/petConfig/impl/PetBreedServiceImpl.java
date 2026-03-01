package com.jacob.service.petConfig.impl;


import com.jacob.common.model.petConfig.entity.PetBreed;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petConfig.PetBreedDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petConfig.PetBreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetBreedServiceImpl extends BaseServiceImpl<PetBreedDao, PetBreed> implements PetBreedService {

    @Autowired
    private PetBreedDao petBreedDao;

    @Override
    public SqlDao getDao() {
        return petBreedDao;
    }
}
