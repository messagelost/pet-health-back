package com.jacob.dao.mappers.petConfig;

import com.jacob.common.model.petConfig.entity.VaccineInfo;
import com.jacob.dao.base.SqlDao;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineInfoDao extends SqlDao<VaccineInfo> {
}
