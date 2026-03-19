package com.jacob.common.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class UserSessionManager {

    private static final Map<String, Set<WebSocketSession>> USER_SESSIONS =
            new ConcurrentHashMap<>();

    public void addSession(String userId, WebSocketSession session) {

        USER_SESSIONS
                .computeIfAbsent(userId, k -> new HashSet<>())
                .add(session);

    }

    public void removeSession(String userId, WebSocketSession session) {

        Set<WebSocketSession> sessions = USER_SESSIONS.get(userId);

        if (sessions != null) {

            sessions.remove(session);

            if (sessions.isEmpty()) {
                USER_SESSIONS.remove(userId);
            }

        }
    }

    public Set<WebSocketSession> getSessions(String userId) {

        return USER_SESSIONS.getOrDefault(userId, Collections.emptySet());

    }

    public boolean isOnline(String userId) {

        Set<WebSocketSession> sessions = USER_SESSIONS.get(userId);

        if (sessions == null || sessions.isEmpty()) {
            return false;
        }

        for (WebSocketSession session : sessions) {

            if (session.isOpen()) {
                return true;
            }

        }

        return false;
    }

}