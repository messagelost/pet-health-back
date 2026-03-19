package com.jacob.common.config;

import com.jacob.common.handler.NotifyWebSocketHandler;
import com.jacob.common.interceptor.JwtHandshakeInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Resource
    private NotifyWebSocketHandler notifyWebSocketHandler;
    @Resource
    private JwtHandshakeInterceptor jwtHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        registry.addHandler(notifyWebSocketHandler, "/ws/notify")
                .addInterceptors(jwtHandshakeInterceptor)
                .setAllowedOrigins("*");

    }
}