package com.jacob.dao.mappers.petConfig;

import com.jacob.common.model.petConfig.entity.PetSpecies;
import com.jacob.dao.base.SqlDao;
import org.springframework.stereotype.Repository;

@Repository
public interface PetSpeciesDao extends SqlDao<PetSpecies> {
    PetSpecies getSpeciesByBreed(String breedId);
}
