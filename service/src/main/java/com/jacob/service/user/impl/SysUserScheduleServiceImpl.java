package com.jacob.service.user.impl;

import com.jacob.common.model.user.entity.SysUserSchedule;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.user.SysUserScheduleDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.user.SysUserScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserScheduleServiceImpl extends BaseServiceImpl<SysUserScheduleDao, SysUserSchedule> implements SysUserScheduleService {

    @Autowired
    private SysUserScheduleDao sysUserScheduleDao;

    @Override
    public SqlDao getDao() {
        return sysUserScheduleDao;
    }
}
