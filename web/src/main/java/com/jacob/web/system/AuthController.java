package com.jacob.web.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.model.author.vo.LoginVo;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.user.entity.SysUserInfo;
import com.jacob.common.model.user.entity.SysUserNotifyMsg;
import com.jacob.common.utils.JwtUtil;
import com.jacob.service.user.SysUserInfoService;
import com.jacob.service.user.SysUserNotifyMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SysUserInfoService sysUserInfoService;

    @PostMapping("/login")
    public ResponseVO<LoginVo> login(@RequestBody SysUserInfo user){
        LoginVo vo = sysUserInfoService.login(user);
        return ResponseVO.success(vo);
    }

}
