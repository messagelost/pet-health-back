package com.jacob.web.petConfig;

import cn.hutool.core.bean.BeanUtil;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petConfig.entity.PetNutritionConfig;
import com.jacob.common.model.petConfig.entity.PetNutritionStandard;
import com.jacob.common.model.petConfig.entity.VaccineInfo;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.petConfig.PetNutritionConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/petNutritionConfig")
public class PetNutritionConfigController {
    
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PetNutritionConfigService petNutritionConfigService;

    @PostMapping
    @RequiresPermissions("petConfig:petNutritionConfig:add")
    @ApiPermission(code = "petConfig:petNutritionConfig:add", name = "添加营养成分")
    public ResponseVO<?> add(@RequestBody PetNutritionConfig petNutritionConfig){
        petNutritionConfig.setConfigId(snowflakeIdGenerator.generateIdWithPrefix("C"));
        petNutritionConfig.setCreateUserId(jwtUtil.getCurrentUserId());
        petNutritionConfig.setUpdateUserId(jwtUtil.getCurrentUserId());
        petNutritionConfigService.insertWithBean(petNutritionConfig);

        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petNutritionConfig:update")
    @ApiPermission(code = "petConfig:petNutritionConfig:update", name = "更新营养成分")
    @PutMapping("/{id}")
    public ResponseVO<?> update(@PathVariable("id") String configId, @RequestBody PetNutritionConfig petNutritionConfig){
        petNutritionConfig.setConfigId(configId);
        petNutritionConfig.setUpdateUserId(jwtUtil.getCurrentUserId());
        petNutritionConfigService.updateWithBean(petNutritionConfig);

        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petNutritionConfig:delete")
    @ApiPermission(code = "petConfig:petNutritionConfig:delete", name = "删除营养成分")
    @DeleteMapping("/{id}")
    public ResponseVO<?> delete(@PathVariable("id") String configId){
        petNutritionConfigService.deleteById(configId);
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petNutritionConfig:page")
    @ApiPermission(code = "petConfig:petNutritionConfig:page", name = "分页查询营养成分")
    @GetMapping("/page")
    public ResponseVO<PageResult<PetNutritionConfig>> page(PageQuery page, PetNutritionConfig petNutritionConfig){
        Map<String, Object> params = BeanUtil.beanToMap(page);
        params.putAll(BeanUtil.beanToMap(petNutritionConfig));
        PageResult<PetNutritionConfig> result = petNutritionConfigService.selectPageList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("petConfig:petNutritionConfig:list")
    @ApiPermission(code = "petConfig:petNutritionConfig:list", name = "查询宠物营养成分")
    @GetMapping("/list")
    public ResponseVO<List<PetNutritionConfig>> list(PetNutritionConfig petNutritionConfig){
        Map<String, Object> params = new HashMap<>(BeanUtil.beanToMap(petNutritionConfig));
        List<PetNutritionConfig> result = petNutritionConfigService.selectAllList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("petConfig:petNutritionConfig:child")
    @ApiPermission(code = "petConfig:petNutritionConfig:child", name = "查询非根节点宠物营养成分")
    @GetMapping("/children")
    public ResponseVO<List<PetNutritionConfig>> getChildren(PetNutritionConfig petNutritionConfig){
        Map<String, Object> params = new HashMap<>(BeanUtil.beanToMap(petNutritionConfig));
        List<PetNutritionConfig> result = petNutritionConfigService.selectAllList(params);
        result = result.stream().filter(item -> !item.getParentId().equals("-1")).toList();
        return ResponseVO.success(result);
    }
    
}
