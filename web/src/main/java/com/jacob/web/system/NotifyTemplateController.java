package com.jacob.web.system;

import cn.hutool.core.bean.BeanUtil;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.system.entity.NotifyTemplate;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.system.NotifyTemplateService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifyTemplate")
public class NotifyTemplateController {

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private NotifyTemplateService notifyTemplateService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @RequiresPermissions("system:notifyTemplate:add")
    @ApiPermission(code = "system:notifyTemplate:add", name = "添加通知模板")
    public ResponseVO<?> add(@RequestBody NotifyTemplate notifyTemplate){
        notifyTemplate.setTemplateId(snowflakeIdGenerator.generateIdWithPrefix("T"));
        notifyTemplate.setCreateUserId(jwtUtil.getCurrentUserId());
        notifyTemplate.setUpdateUserId(jwtUtil.getCurrentUserId());
        notifyTemplateService.insertWithBean(notifyTemplate);

        return ResponseVO.success();
    }

    @RequiresPermissions("system:notifyTemplate:update")
    @ApiPermission(code = "system:notifyTemplate:update", name = "更新通知模板")
    @PutMapping("/{id}")
    public ResponseVO<?> update(@PathVariable("id") String vaccineId, @RequestBody NotifyTemplate notifyTemplate){
        notifyTemplate.setTemplateId(vaccineId);
        notifyTemplate.setUpdateUserId(jwtUtil.getCurrentUserId());
        notifyTemplateService.updateWithBean(notifyTemplate);

        return ResponseVO.success();
    }

    @RequiresPermissions("system:notifyTemplate:delete")
    @ApiPermission(code = "system:notifyTemplate:delete", name = "删除通知模板")
    @DeleteMapping("/{id}")
    public ResponseVO<?> delete(@PathVariable("id") String vaccineId){
        notifyTemplateService.deleteById(vaccineId);
        return ResponseVO.success();
    }

    @RequiresPermissions("system:notifyTemplate:page")
    @ApiPermission(code = "system:notifyTemplate:page", name = "分页查询通知模板")
    @GetMapping("/page")
    public ResponseVO<PageResult<NotifyTemplate>> page(PageQuery page, NotifyTemplate notifyTemplate){
        Map<String, Object> params = BeanUtil.beanToMap(page);
        params.putAll(BeanUtil.beanToMap(notifyTemplate));
        PageResult<NotifyTemplate> result = notifyTemplateService.selectPageList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("system:notifyTemplate:list")
    @ApiPermission(code = "system:notifyTemplate:list", name = "查询宠物通知模板")
    @GetMapping("/list")
    public ResponseVO<List<NotifyTemplate>> list(NotifyTemplate notifyTemplate){
        Map<String, Object> params = new HashMap<>(BeanUtil.beanToMap(notifyTemplate));
        List<NotifyTemplate> result = notifyTemplateService.selectAllList(params);
        return ResponseVO.success(result);
    }
}
