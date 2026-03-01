package com.jacob.security.config;

import com.jacob.security.filter.JwtFilter;
import com.jacob.security.realm.JwtRealm;
import jakarta.servlet.Filter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Autowired
    private JwtRealm jwtRealm;
    @Autowired
    private JwtFilter jwtFilter;

    // 配置 SecurityManager（核心管理器）
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置自定义 Realm
        securityManager.setRealm(jwtRealm);

        // 禁用 Shiro 原生 Session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    // 配置 Shiro 过滤器（URL 权限规则 + JWT 过滤器）
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        // 自定义过滤器：注册 JWT 过滤器
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("jwt", jwtFilter);
        factoryBean.setFilters(filterMap);

        // URL 权限规则
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // TODO 放行：登录接口、注册接口、静态资源
        filterChainDefinitionMap.put("/api/auth/login", "anon");
        filterChainDefinitionMap.put("/api/auth/register", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");

        // 拦截：所有其他接口都需要 JWT 认证
        filterChainDefinitionMap.put("/**", "jwt");

        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return factoryBean;
    }
}
