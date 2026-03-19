package com.jacob.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacob.common.model.author.entity.SysMenu;
import com.jacob.common.model.system.entity.NotifyTemplate;
import com.jacob.common.redis.RedisConstant;
import com.jacob.common.redis.RedisUtils;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.system.NotifyTemplateDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.system.NotifyTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class NotifyTemplateServiceImpl extends BaseServiceImpl<NotifyTemplateDao, NotifyTemplate> implements NotifyTemplateService {

    @Autowired
    private NotifyTemplateDao notifyTemplateDao;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public SqlDao getDao() {
        return notifyTemplateDao;
    }

    @Override
    public NotifyTemplate getByEventTypeInCache(Integer eventType) {
        String redisKey = RedisConstant.NOTIFY_TEMPLATE.getCode() + eventType;
        Object obj = redisUtils.get(redisKey);

        if(obj == null) {
            NotifyTemplate template = getOne(new LambdaQueryWrapper<NotifyTemplate>().eq(NotifyTemplate::getEventType, eventType));
            if (template != null) {
                redisUtils.set(redisKey, template);
            }
            return template;
        } else {
            if (obj instanceof NotifyTemplate) {
                return (NotifyTemplate) obj;
            } else {
                return objectMapper.convertValue(obj, NotifyTemplate.class);
            }
        }
    }
}
