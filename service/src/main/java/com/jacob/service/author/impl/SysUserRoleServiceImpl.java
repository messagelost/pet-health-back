package com.jacob.service.author.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacob.common.model.author.entity.SysMenu;
import com.jacob.common.model.author.entity.SysRole;
import com.jacob.common.model.author.entity.SysUserRole;
import com.jacob.common.redis.RedisConstant;
import com.jacob.common.redis.RedisUtils;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.author.SysUserRoleDao;
import com.jacob.service.author.SysUserRoleService;
import com.jacob.service.base.impl.BaseServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleDao, SysUserRole> implements SysUserRoleService {

    @Resource
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public SqlDao getDao() {
        return sysUserRoleDao;
    }

    @Override
    public List<SysUserRole> listUserRoles(String userId) {
        return sysUserRoleDao.listUserRolesByUserId(userId);
    }

    @Override
    public List<SysUserRole> listUserRolesInCache(String userId) {
        String redisKey = RedisConstant.USER_ROLES.getCode() + userId;
        List<Object> list = redisUtils.getQueueList(redisKey);
        if (list != null && !list.isEmpty()) {
            return list.stream()
                    .filter(Objects::nonNull)
                    .map(obj -> {
                        try {
                            String jsonStr = obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
                            return objectMapper.readValue(jsonStr, SysUserRole.class);
                        } catch (JsonProcessingException e) {
                            log.error(e.getMessage());
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
        }else{
            List<SysUserRole> result = listUserRoles(userId);
            redisUtils.lSet(redisKey, result.stream().map(role -> (Object) role).collect(Collectors.toList()), 7200);
            return result;
        }
    }

    @Override
    public void clearCache() {
        String redisRoleKey = RedisConstant.USER_ROLES.getCode();
        String redisMenuKey = RedisConstant.USER_MENUS.getCode();
        redisUtils.del(redisRoleKey);
        redisUtils.del(redisMenuKey);
    }

}
