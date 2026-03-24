package com.jacob.dao.mappers.petConfig;

import com.jacob.common.model.petConfig.entity.VaccineInfo;
import com.jacob.common.model.petData.vo.VaccineCoverVo;
import com.jacob.dao.base.SqlDao;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface VaccineInfoDao extends SqlDao<VaccineInfo> {
    List<VaccineInfo> selectSuggestVaccine(Map<String, Object> params);

    List<VaccineCoverVo> getVaccineCover(Map<String, Object> params);
}
