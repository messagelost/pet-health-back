package com.jacob.service.petConfig.impl;

import com.jacob.common.model.petConfig.entity.PetSpecies;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petConfig.PetSpeciesDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petConfig.PetSpeciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetSpeciesServiceImpl extends BaseServiceImpl<PetSpeciesDao, PetSpecies> implements PetSpeciesService {

    @Autowired
    private PetSpeciesDao petSpeciesDao;

    @Override
    public SqlDao getDao() {
        return petSpeciesDao;
    }

    @Override
    public PetSpecies getSpeciesByBreed(String breedId) {
        return petSpeciesDao.getSpeciesByBreed(breedId);
    }
}
