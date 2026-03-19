package com.jacob.service.notify.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacob.common.manager.UserSessionManager;
import com.jacob.common.model.petData.entity.PetEventReminder;
import com.jacob.common.model.user.entity.SysUserNotifyMsg;
import com.jacob.common.redis.RedisConstant;
import com.jacob.common.redis.RedisUtils;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.user.SysUserNotifyMsgService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.Executor;

@Slf4j
@Service
public class BaseSender {

    @Resource
    private UserSessionManager sessionManager;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private SysUserNotifyMsgService sysUserNotifyMsgService;
    @Autowired
    private RedisUtils redisUtils;
    @Resource(name = "notifyExecutor")
    private Executor notifyExecutor;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void push(String userId, PetEventReminder reminder) {

        if (!trySend(reminder.getReminderId())) {
            log.info("重复通知，已跳过 reminderId={}", reminder.getReminderId());
            return;
        }

        SysUserNotifyMsg message = getMsg(reminder);

        notifyExecutor.execute(() -> sysUserNotifyMsgService.save(message));

        if( !sessionManager.isOnline(userId) ){
            return;
        }

        try {

            Set<WebSocketSession> sessions =
                    sessionManager.getSessions(userId);

            if (sessions.isEmpty()) {
                return;
            }

            String json = MAPPER.writeValueAsString(message);

            for (WebSocketSession session : sessions) {

                if (session.isOpen()) {

                    session.sendMessage(new TextMessage(json));

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public SysUserNotifyMsg getMsg(PetEventReminder reminder) {

        SysUserNotifyMsg message = new SysUserNotifyMsg();
        LocalDateTime now = LocalDateTime.now();

        message.setMsgId(snowflakeIdGenerator.generateIdWithPrefix("M"));
        message.setUserId(reminder.getUserId());
        message.setStatus(0);
        message.setTitle(reminder.getTitle());
        message.setContent(reminder.getContent());
        message.setCreateTime(now);
        message.setUpdateTime(now);
        message.setCreateUserId("system");

        return message;
    }

    public boolean trySend(String reminderId){

        String key = RedisConstant.NOTIFY_SEND.getCode() + reminderId;

        return redisUtils.setIfAbsent(key, "1", 60 * 60);

    }

}
