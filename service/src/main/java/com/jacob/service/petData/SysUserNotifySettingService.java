package com.jacob.service.petData;

import com.jacob.common.model.user.entity.SysUserNotifySetting;
import com.jacob.service.base.BaseService;

import java.util.List;

public interface SysUserNotifySettingService extends BaseService<SysUserNotifySetting> {
    List<String> getConfigChannel(String userId);
}
