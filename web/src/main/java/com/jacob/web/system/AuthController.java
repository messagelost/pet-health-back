package com.jacob.web.system;

import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.user.entity.SysUserInfo;
import com.jacob.service.user.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SysUserInfoService sysUserInfoService;

    @PostMapping("/login")
    public ResponseVO<String> login(@RequestBody SysUserInfo user){
        String jwt = sysUserInfoService.login(user);
        return ResponseVO.success(jwt);
    }
}
