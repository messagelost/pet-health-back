package com.jacob.service.petConfig.impl;

import com.jacob.common.model.petConfig.entity.PetHealthIndicatorRule;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petConfig.PetHealthIndicatorRuleDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petConfig.PetHealthIndicatorRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetHealthIndicatorRuleServiceImpl extends BaseServiceImpl<PetHealthIndicatorRuleDao, PetHealthIndicatorRule> implements PetHealthIndicatorRuleService {

    @Autowired
    private PetHealthIndicatorRuleDao petHealthIndicatorRuleDao;

    @Override
    public SqlDao getDao() {
        return petHealthIndicatorRuleDao;
    }
}
