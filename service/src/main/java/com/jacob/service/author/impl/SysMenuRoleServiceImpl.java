package com.jacob.service.author.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.model.author.entity.SysMenu;
import com.jacob.common.model.author.entity.SysMenuRole;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.author.SysMenuRoleDao;
import com.jacob.service.author.SysMenuRoleService;
import com.jacob.service.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysMenuRoleServiceImpl extends BaseServiceImpl<SysMenuRoleDao, SysMenuRole> implements SysMenuRoleService {

    @Autowired
    private SysMenuRoleDao sysMenuRoleDao;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public SqlDao getDao() {
        return sysMenuRoleDao;
    }

    @Override
    public List<SysMenu> selectMenuByRoleId(String roleId) {
        return sysMenuRoleDao.selectMenuByRoleId(roleId);
    }

    @Override
    public void saveOrUpdateList(String roleId, List<String> menuIdList) {
        String userId = jwtUtil.getCurrentUserId();
        List<String> existMenuIdList = new ArrayList<>(list(new LambdaQueryWrapper<SysMenuRole>().eq(SysMenuRole::getRoleId, roleId)).stream().map(SysMenuRole::getMenuId).toList());
        menuIdList.forEach(menuId -> {
            if(!existMenuIdList.contains(menuId)){
                SysMenuRole menuRole = new SysMenuRole();
                menuRole.setMenuRoleId(snowflakeIdGenerator.generateIdWithPrefix("MR"));
                menuRole.setRoleId(roleId);
                menuRole.setMenuId(menuId);
                menuRole.setCreateUserId(userId);
                menuRole.setUpdateUserId(userId);
                save(menuRole);
            }else{
                existMenuIdList.remove(menuId);
            }
        });
        existMenuIdList.forEach(menuId -> {
            remove(new LambdaQueryWrapper<SysMenuRole>().eq(SysMenuRole::getMenuId, menuId).eq(SysMenuRole::getRoleId, roleId));
        });
    }
}
