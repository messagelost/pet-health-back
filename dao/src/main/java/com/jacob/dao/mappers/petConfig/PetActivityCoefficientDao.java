package com.jacob.dao.mappers.petConfig;

import com.jacob.common.model.petConfig.entity.PetActivityCoefficient;
import com.jacob.dao.base.SqlDao;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetActivityCoefficientDao extends SqlDao<PetActivityCoefficient> {
    List<PetActivityCoefficient> selectByBreedId(String breedId);

    @Select("${codeSql}")
    Integer getByCaseSql(@Param("codeSql") String sql);
}
