package com.jacob.security.filter;

import com.alibaba.fastjson2.JSON;
import com.jacob.common.exception.HttpStatusEnum;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.utils.JwtUtil;
import com.jacob.security.realm.JwtRealm;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Component
public class JwtFilter extends BasicHttpAuthenticationFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    private JwtUtil jwtUtil;

    // 核心：拦截请求，校验 Token 并完成 Shiro 认证
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // System.out.println("请求路径>>>>>>>>>>>>>"+httpRequest.getRequestURI());
        // 从 Header 中获取 Token
        String token = extractToken(httpRequest);
        if(token == null){
            return true;
        }
        if (!jwtUtil.validateToken(token)) {
            return false; // Token 无效，进入 onAccessDenied 处理
        }

        // 封装 JwtToken，调用 Shiro 认证
        JwtRealm.JwtToken jwtToken = new JwtRealm.JwtToken(token);
        try {
            // 交给 Realm 做认证
            getSubject(request, response).login(jwtToken);
            return true; // 认证成功
        } catch (Exception e) {
            return false; // 认证失败
        }
    }

    // Token 无效/缺失时的处理
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        ResponseVO<?> errorResponse = new ResponseVO<>();
        errorResponse.setCode(HttpStatusEnum.UNAUTHORIZED.getCode());
        errorResponse.setMessage(HttpStatusEnum.UNAUTHORIZED.getMsg());
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=UTF-8");
        httpResponse.setStatus(HttpStatusEnum.UNAUTHORIZED.getCode());
        httpResponse.getWriter().write(JSON.toJSONString(errorResponse));
        return false;
    }

    // 跨域支持
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", httpRequest.getHeader("Origin"));
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization,Content-Type");
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    // 提取 Token（从 Authorization Header 中）
    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            return header.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
