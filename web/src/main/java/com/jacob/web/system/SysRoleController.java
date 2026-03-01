package com.jacob.web.system;

import cn.hutool.core.bean.BeanUtil;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.author.entity.SysMenu;
import com.jacob.common.model.author.entity.SysRole;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.author.SysMenuRoleService;
import com.jacob.service.author.SysRoleService;
import com.jacob.service.author.SysUserRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sysRole")
public class SysRoleController {

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysMenuRoleService sysMenuRoleService;

    @PostMapping
    @RequiresPermissions("system:sysRole:add")
    @ApiPermission(code = "system:sysRole:add", name = "添加角色")
    public ResponseVO<?> add(@RequestBody SysRole role){
        role.setRoleId(snowflakeIdGenerator.generateIdWithPrefix("MR"));
        sysRoleService.insertWithBean(role);
        sysMenuRoleService.saveOrUpdateList(role.getRoleId(), role.getMenuIdList());
        return ResponseVO.success();
    }

    @RequiresPermissions("system:sysRole:update")
    @ApiPermission(code = "system:sysRole:update", name = "更新角色")
    @PutMapping("/{id}")
    public ResponseVO<?> update(@PathVariable("id") String roleId, @RequestBody SysRole role){
        sysRoleService.updateWithBean(role);
        sysMenuRoleService.saveOrUpdateList(roleId, role.getMenuIdList());
        return ResponseVO.success();
    }

    @RequiresPermissions("system:sysRole:page")
    @ApiPermission(code = "system:sysRole:page", name = "分页查询角色")
    @GetMapping("/page")
    public ResponseVO<PageResult<SysRole>> page(PageQuery page, SysRole role){
        Map<String, Object> params = BeanUtil.beanToMap(page);
        params.putAll(BeanUtil.beanToMap(role));
        params.put("custom_order_by", "role_sort");
        PageResult<SysRole> result = sysRoleService.selectPageList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("system:sysRole:list")
    @ApiPermission(code = "system:sysRole:list", name = "查询角色")
    @GetMapping("/list")
    public ResponseVO<List<SysRole>> list(SysRole role){
        Map<String, Object> params = new HashMap<>(BeanUtil.beanToMap(role));
        params.put("custom_order_by", "role_sort");
        List<SysRole> result = sysRoleService.selectAllList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("system:sysRole:getRoleMenu")
    @ApiPermission(code = "system:sysRole:getRoleMenu", name = "查询角色权限")
    @GetMapping("/roleMenu/{id}")
    public ResponseVO<List<SysMenu>> getRoleMenu(@PathVariable("id") String roleId){
        List<SysMenu> result = sysMenuRoleService.selectMenuByRoleId(roleId);
        return ResponseVO.success(result);
    }
}
