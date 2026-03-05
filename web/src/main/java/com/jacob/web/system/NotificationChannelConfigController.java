package com.jacob.web.system;

import cn.hutool.core.bean.BeanUtil;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.system.entity.NotificationChannelConfig;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.system.NotificationChannelConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notificationChannelConfig")
public class NotificationChannelConfigController {

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private NotificationChannelConfigService notificationChannelConfigService;

    @PostMapping
    @RequiresPermissions("system:notificationChannelConfig:add")
    @ApiPermission(code = "system:notificationChannelConfig:add", name = "添加通知渠道")
    public ResponseVO<?> add(@RequestBody NotificationChannelConfig notificationChannelConfig){
        notificationChannelConfig.setChannelId(snowflakeIdGenerator.generateIdWithPrefix("C"));
        notificationChannelConfig.setCreateUserId(jwtUtil.getCurrentUserId());
        notificationChannelConfig.setUpdateUserId(jwtUtil.getCurrentUserId());
        notificationChannelConfigService.insertWithBean(notificationChannelConfig);

        return ResponseVO.success();
    }

    @RequiresPermissions("system:notificationChannelConfig:update")
    @ApiPermission(code = "system:notificationChannelConfig:update", name = "更新通知渠道")
    @PutMapping("/{id}")
    public ResponseVO<?> update(@PathVariable("id") String channelId, @RequestBody NotificationChannelConfig notificationChannelConfig){
        notificationChannelConfig.setChannelId(channelId);
        notificationChannelConfig.setUpdateUserId(jwtUtil.getCurrentUserId());
        notificationChannelConfigService.updateWithBean(notificationChannelConfig);

        return ResponseVO.success();
    }

    @RequiresPermissions("system:notificationChannelConfig:delete")
    @ApiPermission(code = "system:notificationChannelConfig:delete", name = "删除通知渠道")
    @DeleteMapping("/{id}")
    public ResponseVO<?> delete(@PathVariable("id") String channelId){
        notificationChannelConfigService.deleteById(channelId);
        return ResponseVO.success();
    }

    @RequiresPermissions("system:notificationChannelConfig:page")
    @ApiPermission(code = "system:notificationChannelConfig:page", name = "分页查询通知渠道")
    @GetMapping("/page")
    public ResponseVO<PageResult<NotificationChannelConfig>> page(PageQuery page, NotificationChannelConfig notificationChannelConfig){
        Map<String, Object> params = BeanUtil.beanToMap(page);
        params.putAll(BeanUtil.beanToMap(notificationChannelConfig));
        PageResult<NotificationChannelConfig> result = notificationChannelConfigService.selectPageList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("system:notificationChannelConfig:list")
    @ApiPermission(code = "system:notificationChannelConfig:list", name = "查询宠物通知渠道")
    @GetMapping("/list")
    public ResponseVO<List<NotificationChannelConfig>> list(NotificationChannelConfig notificationChannelConfig){
        Map<String, Object> params = new HashMap<>(BeanUtil.beanToMap(notificationChannelConfig));
        List<NotificationChannelConfig> result = notificationChannelConfigService.selectAllList(params);
        return ResponseVO.success(result);
    }

}
