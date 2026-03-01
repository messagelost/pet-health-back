package com.jacob.web.petConfig;

import cn.hutool.core.bean.BeanUtil;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petConfig.entity.PetBreed;
import com.jacob.common.model.petConfig.entity.PetHealthIndicator;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.petConfig.PetHealthIndicatorService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/petHealthIndicator")
public class PetHealthIndicatorController {

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PetHealthIndicatorService petHealthIndicatorService;

    @PostMapping
    @RequiresPermissions("petConfig:petHealthIndicator:add")
    @ApiPermission(code = "petConfig:petHealthIndicator:add", name = "添加健康指标")
    public ResponseVO<?> add(@RequestBody PetHealthIndicator petHealthIndicator){
        petHealthIndicator.setIndicatorId(snowflakeIdGenerator.generateIdWithPrefix("I"));
        petHealthIndicator.setCreateUserId(jwtUtil.getCurrentUserId());
        petHealthIndicator.setUpdateUserId(jwtUtil.getCurrentUserId());
        petHealthIndicatorService.insertWithBean(petHealthIndicator);
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petHealthIndicator:update")
    @ApiPermission(code = "petConfig:petHealthIndicator:update", name = "更新健康指标")
    @PutMapping("/{id}")
    public ResponseVO<?> update(@PathVariable("id") String indicatorId, @RequestBody PetHealthIndicator petHealthIndicator){
        petHealthIndicator.setIndicatorId(indicatorId);
        petHealthIndicator.setUpdateUserId(jwtUtil.getCurrentUserId());
        petHealthIndicatorService.updateWithBean(petHealthIndicator);
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petHealthIndicator:delete")
    @ApiPermission(code = "petConfig:petHealthIndicator:delete", name = "删除健康指标")
    @DeleteMapping("/{id}")
    public ResponseVO<?> delete(@PathVariable("id") String indicatorId){
        petHealthIndicatorService.deleteById(indicatorId);
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petHealthIndicator:page")
    @ApiPermission(code = "petConfig:petHealthIndicator:page", name = "分页查询健康指标")
    @GetMapping("/page")
    public ResponseVO<PageResult<PetHealthIndicator>> page(PageQuery page, PetHealthIndicator petHealthIndicator){
        Map<String, Object> params = BeanUtil.beanToMap(page);
        params.putAll(BeanUtil.beanToMap(petHealthIndicator));
        PageResult<PetHealthIndicator> result = petHealthIndicatorService.selectPageList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("petConfig:petHealthIndicator:list")
    @ApiPermission(code = "petConfig:petHealthIndicator:list", name = "查询健康指标")
    @GetMapping("/list")
    public ResponseVO<List<PetHealthIndicator>> list(PetHealthIndicator petHealthIndicator){
        Map<String, Object> params = new HashMap<>(BeanUtil.beanToMap(petHealthIndicator));
        List<PetHealthIndicator> result = petHealthIndicatorService.selectAllList(params);
        return ResponseVO.success(result);
    }

}
