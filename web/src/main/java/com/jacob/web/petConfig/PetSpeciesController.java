package com.jacob.web.petConfig;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.author.entity.SysRole;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petConfig.entity.PetBreed;
import com.jacob.common.model.petConfig.entity.PetLifeStage;
import com.jacob.common.model.petConfig.entity.PetSpecies;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.petConfig.PetBreedService;
import com.jacob.service.petConfig.PetLifeStageService;
import com.jacob.service.petConfig.PetSpeciesService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/petSpecies")
public class PetSpeciesController {

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private PetSpeciesService petSpeciesService;
    @Autowired
    private PetLifeStageService petLifeStageService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PetBreedService petBreedService;

    @PostMapping
    @RequiresPermissions("petConfig:petSpecies:add")
    @ApiPermission(code = "petConfig:petSpecies:add", name = "添加宠物种类")
    public ResponseVO<?> add(@RequestBody PetSpecies petSpecies){
        petSpecies.setSpeciesId(snowflakeIdGenerator.generateIdWithPrefix("MR"));
        petSpecies.setCreateUserId(jwtUtil.getCurrentUserId());
        petSpecies.setUpdateUserId(jwtUtil.getCurrentUserId());
        petSpeciesService.insertWithBean(petSpecies);
        petLifeStageService.saveOrUpdateStage(petSpecies.getSpeciesId(), petSpecies.getLifeStageList());
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petSpecies:update")
    @ApiPermission(code = "petConfig:petSpecies:update", name = "更新宠物种类")
    @PutMapping("/{id}")
    public ResponseVO<?> update(@PathVariable("id") String speciesId, @RequestBody PetSpecies petSpecies){
        petSpecies.setSpeciesId(speciesId);
        petSpecies.setUpdateUserId(jwtUtil.getCurrentUserId());
        petSpeciesService.updateWithBean(petSpecies);
        petLifeStageService.saveOrUpdateStage(petSpecies.getSpeciesId(), petSpecies.getLifeStageList());
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petSpecies:delete")
    @ApiPermission(code = "petConfig:petSpecies:delete", name = "删除宠物种类")
    @DeleteMapping("/{id}")
    public ResponseVO<?> delete(@PathVariable("id") String speciesId){
        petSpeciesService.deleteById(speciesId);
        petLifeStageService.remove(new LambdaQueryWrapper<PetLifeStage>().eq(PetLifeStage::getSpeciesId, speciesId));
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petSpecies:page")
    @ApiPermission(code = "petConfig:petSpecies:page", name = "分页查询宠物种类")
    @GetMapping("/page")
    public ResponseVO<PageResult<PetSpecies>> page(PageQuery page, PetSpecies petSpecies){
        Map<String, Object> params = BeanUtil.beanToMap(page);
        params.putAll(BeanUtil.beanToMap(petSpecies));
        PageResult<PetSpecies> result = petSpeciesService.selectPageList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("petConfig:petSpecies:list")
    @ApiPermission(code = "petConfig:petSpecies:list", name = "查询宠物种类")
    @GetMapping("/list")
    public ResponseVO<List<PetSpecies>> list(PetSpecies petSpecies){
        Map<String, Object> params = new HashMap<>(BeanUtil.beanToMap(petSpecies));
        List<PetSpecies> result = petSpeciesService.selectAllList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("petConfig:petSpecies:listStages")
    @ApiPermission(code = "petConfig:petSpecies:listStages", name = "查询宠物生命周期")
    @GetMapping("/listStages/{id}")
    public ResponseVO<List<PetLifeStage>> listStages(@PathVariable("id") String speciesId){
        List<PetLifeStage> result = petLifeStageService.list(new LambdaQueryWrapper<PetLifeStage>()
                .eq(PetLifeStage::getSpeciesId, speciesId)
                .orderByAsc(PetLifeStage::getSort)
        );
        return ResponseVO.success(result);
    }

    @RequiresPermissions("petConfig:petSpecies:listStagesByBreed")
    @ApiPermission(code = "petConfig:petSpecies:listStagesByBreed", name = "品种查询宠物生命周期")
    @GetMapping("/listStagesByBreed/{id}")
    public ResponseVO<List<PetLifeStage>> listStagesByBreed(@PathVariable("id") String breedId){
        String speciesId = petBreedService.getOne(new LambdaQueryWrapper<PetBreed>().eq(PetBreed::getBreedId, breedId)).getSpeciesId();
        List<PetLifeStage> result = petLifeStageService.list(new LambdaQueryWrapper<PetLifeStage>()
                .eq(PetLifeStage::getSpeciesId, speciesId)
                .orderByAsc(PetLifeStage::getSort)
        );
        return ResponseVO.success(result);
    }

    @RequiresPermissions("petConfig:petSpecies:deleteStage")
    @ApiPermission(code = "petConfig:petSpecies:deleteStage", name = "删除宠物生命周期")
    @DeleteMapping("/deleteStage/{id}")
    public ResponseVO<List<PetLifeStage>> deleteStage(@PathVariable("id") String stageId){
        petLifeStageService.deleteById(stageId);

        return ResponseVO.success();
    }

}
