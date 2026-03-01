package com.jacob.service.author.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacob.common.model.author.entity.SysMenu;
import com.jacob.common.model.author.entity.SysUserRole;
import com.jacob.common.model.author.enums.MenuType;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.redis.RedisConstant;
import com.jacob.common.redis.RedisUtils;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.author.SysMenuDao;
import com.jacob.service.author.SysMenuService;
import com.jacob.service.base.impl.BaseServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuDao, SysMenu> implements SysMenuService {

    @Resource
    private SysMenuDao sysMenuDao;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public SqlDao getDao() {
        return sysMenuDao;
    }

    @Override
    public List<SysMenu> listMenuByUserId(String userId) {
        return sysMenuDao.selectMenuByUserId(userId);
    }

    @Override
    public List<SysMenu> listMenuByUserIdInCache(String userId) {
        String redisKey = RedisConstant.USER_MENUS.getCode() + userId;
        List<Object> list = redisUtils.getQueueList(redisKey);
        if (list != null && !list.isEmpty()) {
            return list.stream()
                    .filter(Objects::nonNull)
                    .map(obj -> {
                        try {
                            String jsonStr = obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
                            return objectMapper.readValue(jsonStr, SysMenu.class);
                        } catch (JsonProcessingException e) {
                            log.error(e.getMessage());
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
        }else{
            List<SysMenu> result = listMenuByUserId(userId);
            redisUtils.lSet(redisKey, result.stream().map(menu -> (Object) menu).collect(Collectors.toList()), 7200);
            return result;
        }
    }

    @Override
    public void registerMenu(String code, String name) {
        // code拆分
        String[] codeArr = code.split(":");
        if(codeArr.length != 3){
            log.error("权限编码格式错误：{}", code);
            return;
        }
        LocalDateTime now = LocalDateTime.now();

        String catalogStr = codeArr[0] + ":#:#";
        String menuStr = codeArr[0] + ":" + codeArr[1] + ":#";

        // 目录
        SysMenu existCatalog = getOne(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getAuthorStr, catalogStr));
        if(existCatalog == null){
            log.info("创建目录权限：{}", catalogStr);
            existCatalog = new SysMenu();
            existCatalog.setMenuId(snowflakeIdGenerator.generateIdWithPrefix("M"));
            existCatalog.setParentId("-1");
            existCatalog.setName(codeArr[0]);
            existCatalog.setTitle(codeArr[0]);
            existCatalog.setMenuType(MenuType.CATALOG.getCode());
            existCatalog.setPath("/"+codeArr[0]);
            existCatalog.setActiveMenu("/"+codeArr[0]);

            existCatalog.setAuthorStr(catalogStr);
            existCatalog.setVisible(1);
            existCatalog.setStatus(1);
            existCatalog.setCreateTime(now);
            existCatalog.setUpdateTime(now);
            existCatalog.setCreateUserId("admin");
            existCatalog.setUpdateUserId("admin");
            save(existCatalog);
        }

        // 菜单
        SysMenu existMenu = getOne(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getAuthorStr, menuStr));
        if(existMenu == null){
            log.info("创建菜单权限：{}", menuStr);
            existMenu = new SysMenu();
            existMenu.setMenuId(snowflakeIdGenerator.generateIdWithPrefix("M"));
            existMenu.setParentId(existCatalog.getMenuId());
            existMenu.setName(codeArr[1]);
            existMenu.setTitle(codeArr[1]);
            existMenu.setMenuType(MenuType.MENU.getCode());
            existMenu.setPath("/"+codeArr[0]+"/"+codeArr[1]);
            existMenu.setActiveMenu("/"+codeArr[0]+"/"+codeArr[1]);

            existMenu.setAuthorStr(menuStr);
            existMenu.setVisible(1);
            existMenu.setStatus(1);
            existMenu.setCreateTime(now);
            existMenu.setUpdateTime(now);
            existMenu.setCreateUserId("admin");
            existMenu.setUpdateUserId("admin");
            save(existMenu);
        }

        // 接口
        SysMenu exist = getOne(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getAuthorStr, code));
        if(exist != null && !exist.getTitle().equals(name)){
            log.info("更新接口权限：{}", code);
            update(new LambdaUpdateWrapper<SysMenu>()
                    .set(SysMenu::getTitle, name)
                    .eq(SysMenu::getAuthorStr, code)
            );
        }else if(exist == null) {
            log.info("创建接口权限：{}", code);
            SysMenu menu = new SysMenu();
            menu.setMenuId(snowflakeIdGenerator.generateIdWithPrefix("M"));
            menu.setParentId(existMenu.getMenuId());
            menu.setName(codeArr[2]);
            menu.setTitle(name);
            menu.setMenuType(MenuType.BUTTON.getCode());
            menu.setAuthorStr(code);

            menu.setVisible(1);
            menu.setStatus(1);
            menu.setCreateTime(now);
            menu.setUpdateTime(now);
            menu.setCreateUserId("admin");
            menu.setUpdateUserId("admin");
            save(menu);
        }
    }

    @Override
    public void clearCache(String userId) {
        String redisKey = RedisConstant.USER_MENUS.getCode() + userId;
        redisUtils.del(redisKey);
    }

}
