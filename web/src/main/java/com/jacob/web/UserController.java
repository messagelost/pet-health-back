package com.jacob.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.model.user.entity.SysUserInfo;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.user.userInfo.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private SysUserInfoService sysUserInfoService;

    @Autowired
    private SnowflakeIdGenerator customSnowflakeIdGenerator;

    @GetMapping("/user/{name}")
    public String user(@PathVariable String name){
        LocalDate now = LocalDate.now();

        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserId(customSnowflakeIdGenerator.generateIdWithPrefix("U"));
        sysUserInfo.setUsername(name);
        sysUserInfo.setPassword("123456");
        sysUserInfo.setStatus(1);
        sysUserInfo.setCreateUser("admin");
        sysUserInfo.setUpdateUser("admin");
        sysUserInfo.setCreateTime(now);
        sysUserInfo.setUpdateTime(now);
        sysUserInfoService.save(sysUserInfo);

        return name;
    }

    @GetMapping("/getUser")
    public List<SysUserInfo> getUser(){
        return sysUserInfoService.list(new LambdaQueryWrapper<SysUserInfo>().select(SysUserInfo::getUserId,SysUserInfo::getUsername));
    }
}
