package com.jacob.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacob.common.manager.UserSessionManager;
import com.jacob.common.utils.JwtUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

@Slf4j
@Component
public class NotifyWebSocketHandler extends TextWebSocketHandler {

    @Resource
    private UserSessionManager sessionManager;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String userId = getUserId(session);

        session.getAttributes().put("userId", userId);

        sessionManager.addSession(userId, session);

        log.info("用户连接 WebSocket: {}", userId);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

        Object userIdObj = session.getAttributes().get("userId");

        if (userIdObj == null) {
            log.warn("WebSocket关闭但userId不存在");
            return;
        }

        String userId = userIdObj.toString();

        if (userId != null) {
            sessionManager.removeSession(userId, session);
        }

        log.info("用户断开 WebSocket: {}", userId);

    }

    private String getUserId(WebSocketSession session) {

        String query = session.getUri().getQuery();

        String[] params = query.split("=");

        String token = params[1];

        return jwtUtil.getUserId(token);

    }

}