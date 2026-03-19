package com.jacob.service.user;

import com.jacob.common.model.user.entity.SysUserSchedule;
import com.jacob.service.base.BaseService;

public interface SysUserScheduleService extends BaseService<SysUserSchedule> {
    void addScheduleByScan();
}
