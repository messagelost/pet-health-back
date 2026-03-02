package com.jacob.web.system;

import cn.hutool.core.bean.BeanUtil;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.author.entity.SysMenu;
import com.jacob.common.model.author.enums.MenuTypeEnum;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.utils.JwtUtil;
import com.jacob.service.author.SysMenuService;
import com.jacob.service.author.SysUserRoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/sysMenu")
public class SysMenuController{

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @GetMapping("/listMenu")
    public ResponseVO<List<SysMenu>> listMenu(HttpServletRequest request){
        // 从请求头获取
        String userId = jwtUtil.getCurrentUserId();

        List<SysMenu> result = sysMenuService.listMenuByUserIdInCache(userId)
                .stream()
                .filter(m -> !Objects.equals(m.getMenuType(), MenuTypeEnum.BUTTON.getCode())).toList();
        return ResponseVO.success(result);
    }

    @GetMapping("/getTreeMenu")
    @ApiPermission(code = "system:sysMenu:getTreeMenu", name = "全部菜单列表")
    @RequiresPermissions("system:sysMenu:getTreeMenu")
    public ResponseVO<PageResult<SysMenu>> getAllMenu(PageQuery page, String parentId){
        Map<String, Object> params = BeanUtil.beanToMap(page);
        if(parentId == null || parentId.isEmpty()){
            parentId = "-1";
        }
        params.put("parentId", parentId);
        PageResult<SysMenu> result = sysMenuService.selectPageList(params);
        return ResponseVO.success(result);
    }

    @GetMapping("/catalog")
    @ApiPermission(code = "system:sysMenu:getCatalog", name = "获取父级菜单")
    @RequiresPermissions("system:sysMenu:getCatalog")
    public ResponseVO<List<SysMenu>> getCatalog(Integer catalogType){
        Map<String, Object> params = new HashMap<>();
        params.put("catalogType", catalogType);
        List<SysMenu> result = sysMenuService.selectAllList(params);
        return ResponseVO.success(result);
    }

    @PutMapping("/{id}")
    @ApiPermission(code = "system:sysMenu:update", name = "修改菜单")
    @RequiresPermissions("system:sysMenu:update")
    public ResponseVO<?> updateMenu(@RequestBody SysMenu menu, @PathVariable("id") String menuId){
        menu.setMenuId(menuId);
        menu.setUpdateTime(LocalDateTime.now());
        menu.setUpdateUserId(jwtUtil.getCurrentUserId());
        sysMenuService.updateWithBean(menu);
        return ResponseVO.success();
    }

}
