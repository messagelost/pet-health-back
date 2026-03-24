package com.jacob.service.petConfig;

import com.jacob.common.model.petConfig.entity.VaccineInfo;
import com.jacob.common.model.petData.vo.VaccineCoverVo;
import com.jacob.service.base.BaseService;

import java.util.List;
import java.util.Map;

public interface VaccineInfoService extends BaseService<VaccineInfo> {
    List<VaccineInfo> selectSuggestVaccine(Map<String, Object> vp);

    List<VaccineCoverVo> getCover(Map<String, Object> params);
}
