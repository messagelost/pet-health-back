package com.jacob.security.realm;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.model.author.entity.SysMenu;
import com.jacob.common.model.author.entity.SysMenuRole;
import com.jacob.common.model.author.entity.SysUserRole;
import com.jacob.common.model.user.entity.SysUserInfo;
import com.jacob.common.utils.JwtUtil;
import com.jacob.service.author.SysMenuService;
import com.jacob.service.author.SysUserRoleService;
import com.jacob.service.user.SysUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtRealm extends AuthorizingRealm {
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken; // 自定义 JWT Token 类型
    }

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SysUserInfoService sysUserInfoService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    public record JwtToken(String token) implements AuthenticationToken {
        // 身份信息
        @Override
        public Object getPrincipal() {
            return token;
        }

        // 凭证信息
        @Override
        public Object getCredentials() {
            return token;
        }
    }

    // 授权逻辑
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 从 principals 中获取用户ID
        String userId = principals.getPrimaryPrincipal().toString();
        log.info("授权用户ID：{}", userId);

        // 查询用户的角色
        Set<String> roles = sysUserRoleService.listUserRoles(userId).stream().map(SysUserRole::getRoleStr).collect(Collectors.toSet());
        // 查询用户的权限
        Set<String> permissions = sysMenuService.listMenuByUserId(userId).stream().map(SysMenu::getAuthorStr).collect(Collectors.toSet());
        log.info("授权角色：{}", roles);

        // 封装角色和权限信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }

    // 认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取 JWT Token
        String jwtToken = (String) token.getPrincipal();

        // 验证 Token 有效性
        if (!jwtUtil.validateToken(jwtToken)) {
            throw new ExpiredCredentialsException("Token 已过期或无效");
        }

        // 从 Token 中获取用户ID，查询用户是否存在
        String userId = jwtUtil.getUserId(jwtToken);
        // TODO 判断账号是否有效
        SysUserInfo user = sysUserInfoService.getById(userId);
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }

        // 返回认证信息
        return new SimpleAuthenticationInfo(userId, jwtToken, getName());
    }
}

