package com.jacob.service.user;

import com.jacob.common.model.author.vo.LoginVo;
import com.jacob.common.model.user.entity.SysUserInfo;
import com.jacob.service.base.BaseService;

import java.io.IOException;
import java.util.Map;

public interface SysUserInfoService extends BaseService<SysUserInfo> {
    LoginVo login(SysUserInfo user);
}
