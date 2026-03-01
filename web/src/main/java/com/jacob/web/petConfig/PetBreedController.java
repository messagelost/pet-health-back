package com.jacob.web.petConfig;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petConfig.entity.PetBreed;
import com.jacob.common.model.petConfig.entity.PetLifeStage;
import com.jacob.common.model.petConfig.entity.PetSpecies;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.petConfig.PetBreedService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/petBreed")
public class PetBreedController {

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private PetBreedService petBreedService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @RequiresPermissions("petConfig:petBreed:add")
    @ApiPermission(code = "petConfig:petBreed:add", name = "添加宠物品种")
    public ResponseVO<?> add(@RequestBody PetBreed petBreed){
        petBreed.setBreedId(snowflakeIdGenerator.generateIdWithPrefix("B"));
        petBreed.setCreateUserId(jwtUtil.getCurrentUserId());
        petBreed.setUpdateUserId(jwtUtil.getCurrentUserId());
        petBreedService.insertWithBean(petBreed);
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petBreed:update")
    @ApiPermission(code = "petConfig:petBreed:update", name = "更新宠物品种")
    @PutMapping("/{id}")
    public ResponseVO<?> update(@PathVariable("id") String breedId, @RequestBody PetBreed petBreed){
        petBreed.setBreedId(breedId);
        petBreed.setUpdateUserId(jwtUtil.getCurrentUserId());
        petBreedService.updateWithBean(petBreed);
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petBreed:delete")
    @ApiPermission(code = "petConfig:petBreed:delete", name = "删除宠物品种")
    @DeleteMapping("/{id}")
    public ResponseVO<?> delete(@PathVariable("id") String breedId){
        petBreedService.deleteById(breedId);
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petBreed:page")
    @ApiPermission(code = "petConfig:petBreed:page", name = "分页查询宠物品种")
    @GetMapping("/page")
    public ResponseVO<PageResult<PetBreed>> page(PageQuery page, PetBreed petBreed){
        Map<String, Object> params = BeanUtil.beanToMap(page);
        params.putAll(BeanUtil.beanToMap(petBreed));
        params.put("custom_order_by", "species_id");
        PageResult<PetBreed> result = petBreedService.selectPageList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("petConfig:petBreed:list")
    @ApiPermission(code = "petConfig:petBreed:list", name = "查询宠物品种")
    @GetMapping("/list")
    public ResponseVO<List<PetBreed>> list(PetBreed petBreed){
        Map<String, Object> params = new HashMap<>(BeanUtil.beanToMap(petBreed));
        params.put("custom_order_by", "species_id");
        List<PetBreed> result = petBreedService.selectAllList(params);
        return ResponseVO.success(result);
    }
}
