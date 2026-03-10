package com.jacob.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {

    @Autowired
    private HttpServletRequest request;

    // JWT 密钥
    @Value("${jwt.secret}")
    private String secret;

    // Token 过期时间
    @Value("${jwt.expire-time}")
    private long expireTime;

    // 生成Token
    public String generateToken(Map<String, Object> claims) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.builder()
                .setClaims(claims) // 自定义载荷
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expireTime)) // 过期时间
                .signWith(key, SignatureAlgorithm.HS256) // 签名算法
                .compact();
    }

    // 解析Token
    public Claims parseToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 验证Token
    public boolean validateToken(String token) {
        try {
            // 添加token格式预检查
            if (token == null || token.trim().isEmpty()) {
                return false;
            }

            // 检查基本格式（JWT通常包含两个点）
            if (token.chars().filter(ch -> ch == '.').count() != 2) {
                return false;
            }

            parseToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("Token已过期: {}", e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            log.warn("Token格式错误: {}", e.getMessage());
            return false;
        } catch (SignatureException e) {
            log.warn("Token签名错误: {}", e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            log.warn("Token参数错误: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Token验证异常: ", e);
            return false;
        }
    }

    // 从 Token 中获取用户ID
    public String getUserId(String token) {
        return parseToken(token).get("userId", String.class);
    }

    public String getCurrentUserId() {
        if(request.getHeader("Authorization") == null){
            throw new UnauthenticatedException();
        }
        return getUserId(request.getHeader("Authorization").substring(7));
    }
}
