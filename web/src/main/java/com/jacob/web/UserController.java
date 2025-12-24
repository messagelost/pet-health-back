package com.jacob.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.user.entity.SysUserInfo;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.user.userInfo.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private SysUserInfoService sysUserInfoService;

    @Autowired
    private SnowflakeIdGenerator customSnowflakeIdGenerator;

    @GetMapping("/add/{name}")
    public ResponseVO<String> user(@PathVariable String name){
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

        return new ResponseVO<>();
    }

    @GetMapping("/getUser")
    public ResponseVO<List<SysUserInfo>> getUser(){
        ResponseVO<List<SysUserInfo>> responseVO = new ResponseVO<>();
        responseVO.setData(sysUserInfoService.list(new LambdaQueryWrapper<SysUserInfo>().select(SysUserInfo::getUserId,SysUserInfo::getUsername)));
        return responseVO;
    }

}
