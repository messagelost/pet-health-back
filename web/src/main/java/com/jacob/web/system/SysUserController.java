package com.jacob.web.system;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.author.entity.SysRole;
import com.jacob.common.model.author.entity.SysUserRole;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.user.entity.SysUserInfo;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.author.SysUserRoleService;
import com.jacob.service.user.SysUserInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserInfoService sysUserInfoService;

    @Autowired
    private SnowflakeIdGenerator customSnowflakeIdGenerator;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SysUserRoleService sysUserRoleService;


    @ApiPermission(code = "system:sysUser:addUser", name = "添加用户")
    @RequiresPermissions("system:sysUser:addUser")
    @PostMapping
    public ResponseVO<?> addUser(@RequestBody SysUserInfo sysUserInfo){
        LocalDateTime now = LocalDateTime.now();

        sysUserInfo.setUserId(customSnowflakeIdGenerator.generateIdWithPrefix("U"));
        sysUserInfo.setStatus(1);
        sysUserInfo.setCreateUserId(jwtUtil.getCurrentUserId());
        sysUserInfo.setUpdateUserId(jwtUtil.getCurrentUserId());
        sysUserInfo.setCreateTime(now);
        sysUserInfo.setUpdateTime(now);
        sysUserInfoService.save(sysUserInfo);

        return ResponseVO.success();
    }

    @ApiPermission(code = "system:sysUser:updateUser", name = "更新用户")
    @RequiresPermissions("system:sysUser:updateUser")
    @PutMapping("/{userId}")
    public ResponseVO<?> updateUser(@RequestBody SysUserInfo sysUserInfo, @PathVariable String userId){
        LocalDateTime now = LocalDateTime.now();

        sysUserInfo.setUserId(userId);
        sysUserInfo.setUpdateUserId(jwtUtil.getCurrentUserId());
        sysUserInfo.setUpdateTime(now);
        sysUserInfoService.updateWithBean(sysUserInfo);

        List<String> sysUserRoles = new ArrayList<>(sysUserRoleService.listUserRoles(userId).stream().map(SysUserRole::getRoleId).toList());
        sysUserInfo.getRoleIdList().forEach(roleId -> {
            if(!sysUserRoles.contains(roleId)){
                SysUserRole ur = new SysUserRole();
                ur.setUserRoleId(customSnowflakeIdGenerator.generateIdWithPrefix("UR"));
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                ur.setCreateTime(now);
                ur.setUpdateTime(now);
                ur.setCreateUserId(jwtUtil.getCurrentUserId());
                ur.setUpdateUserId(jwtUtil.getCurrentUserId());
                sysUserRoleService.save(ur);
            }

            sysUserRoles.remove(roleId);
        });

        if(!sysUserRoles.isEmpty()){
            sysUserRoleService.remove(
                    new LambdaQueryWrapper<SysUserRole>()
                            .in(SysUserRole::getRoleId, sysUserRoles)
                            .eq(SysUserRole::getUserId, userId)
            );
        }

        return ResponseVO.success();
    }

    @ApiPermission(code = "system:sysUser:list", name = "用户列表")
    @RequiresPermissions("system:sysUser:list")
    @GetMapping("/page")
    public ResponseVO<PageResult<SysUserInfo>> page(PageQuery page, SysUserInfo sysUserInfo){
        Map<String, Object> params = BeanUtil.beanToMap(page);
        params.putAll(BeanUtil.beanToMap(sysUserInfo));
        PageResult<SysUserInfo> result = sysUserInfoService.selectPageList(params);
        return ResponseVO.success(result);
    }

    @GetMapping("/userRole/{userId}")
    public ResponseVO<List<SysUserRole>> getUserRole(@PathVariable String userId){
        List<SysUserRole> result = sysUserRoleService.listUserRoles(userId);
        return ResponseVO.success(result);
    }

}
