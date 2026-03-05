package com.jacob.web.petConfig;

import cn.hutool.core.bean.BeanUtil;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petConfig.entity.PetNutritionStandard;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.petConfig.PetNutritionStandardService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/petNutritionStandard")
public class PetNutritionStandardController {

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PetNutritionStandardService petNutritionStandardService;


    @PostMapping
    @RequiresPermissions("petConfig:petNutritionStandard:add")
    @ApiPermission(code = "petConfig:petNutritionStandard:add", name = "添加营养标准")
    public ResponseVO<?> add(@RequestBody PetNutritionStandard petNutritionStandard){
        petNutritionStandard.setStandardId(snowflakeIdGenerator.generateIdWithPrefix("S"));
        petNutritionStandard.setCreateUserId(jwtUtil.getCurrentUserId());
        petNutritionStandard.setUpdateUserId(jwtUtil.getCurrentUserId());
        petNutritionStandardService.insertWithBean(petNutritionStandard);

        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petNutritionStandard:update")
    @ApiPermission(code = "petConfig:petNutritionStandard:update", name = "更新营养标准")
    @PutMapping("/{id}")
    public ResponseVO<?> update(@PathVariable("id") String standardId, @RequestBody PetNutritionStandard petNutritionStandard){
        petNutritionStandard.setStandardId(standardId);
        petNutritionStandard.setUpdateUserId(jwtUtil.getCurrentUserId());
        petNutritionStandardService.updateWithBean(petNutritionStandard);

        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petNutritionStandard:delete")
    @ApiPermission(code = "petConfig:petNutritionStandard:delete", name = "删除营养标准")
    @DeleteMapping("/{id}")
    public ResponseVO<?> delete(@PathVariable("id") String standardId){
        petNutritionStandardService.deleteById(standardId);
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petNutritionStandard:page")
    @ApiPermission(code = "petConfig:petNutritionStandard:page", name = "分页查询营养标准")
    @GetMapping("/page")
    public ResponseVO<PageResult<PetNutritionStandard>> page(PageQuery page, PetNutritionStandard petNutritionStandard){
        Map<String, Object> params = BeanUtil.beanToMap(page);
        params.putAll(BeanUtil.beanToMap(petNutritionStandard));
        PageResult<PetNutritionStandard> result = petNutritionStandardService.selectPageList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("petConfig:petNutritionStandard:list")
    @ApiPermission(code = "petConfig:petNutritionStandard:list", name = "查询宠物营养标准")
    @GetMapping("/list")
    public ResponseVO<List<PetNutritionStandard>> list(PetNutritionStandard petNutritionStandard){
        Map<String, Object> params = new HashMap<>(BeanUtil.beanToMap(petNutritionStandard));
        List<PetNutritionStandard> result = petNutritionStandardService.selectAllList(params);
        return ResponseVO.success(result);
    }
    
}
