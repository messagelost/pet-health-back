package com.jacob.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.model.user.entity.SysUserInfo;
import com.jacob.common.utils.JwtUtil;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.user.SysUserInfoDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.user.SysUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SysUserInfoServiceImpl extends BaseServiceImpl<SysUserInfoDao,SysUserInfo> implements SysUserInfoService {

    @Autowired
    private SysUserInfoDao sysUserInfoDao;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public SqlDao getDao() {
        return sysUserInfoDao;
    }

    @Override
    public String login(SysUserInfo user) {
        String userName = user.getAccount();
        String password = user.getPassword();
        SysUserInfo sysUserInfo = getOne(new LambdaQueryWrapper<SysUserInfo>().eq(SysUserInfo::getUsername, userName));
        if (sysUserInfo == null) {
            throw new RuntimeException("该用户不存在");
        }
        if (!sysUserInfo.getPassword().equals(password)) {
            throw new RuntimeException("密码错误");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", sysUserInfo.getUserId());
        claims.put("userName", userName);
        claims.put("password", password);
        Thread syncThread = new Thread(() -> {
            sysUserInfo.setLastLoginTime(LocalDateTime.now());
            sysUserInfo.setLastLoginIp("");
            updateWithBean(sysUserInfo);
        });
        syncThread.start();
        return jwtUtil.generateToken(claims);
    }
}
