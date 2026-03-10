package com.jacob.web.petData;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petConfig.entity.PetBreed;
import com.jacob.common.model.petConfig.entity.PetSpecies;
import com.jacob.common.model.petConfig.entity.VaccineInfo;
import com.jacob.common.model.petData.entity.PetBasicInfo;
import com.jacob.common.model.petData.vo.PetBasicInfoVo;
import com.jacob.service.petConfig.PetBreedService;
import com.jacob.service.petConfig.PetSpeciesService;
import com.jacob.service.petData.PetBasicInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/petBasicInfo")
public class petBasicInfoController {

    @Autowired
    private PetBasicInfoService petBasicInfoService;
    @Autowired
    private PetSpeciesService petSpeciesService;
    @Autowired
    private PetBreedService petBreedService;


    @PostMapping("/add")
    @RequiresPermissions("petData:petBasicInfo:add")
    @ApiPermission(code = "petData:petBasicInfo:add", name = "添加宠物信息")
    public ResponseVO<?> add(@RequestBody PetBasicInfo petBasicInfo){
        petBasicInfoService.addPet(petBasicInfo);

        return ResponseVO.success();
    }

    @PutMapping("/update")
    @RequiresPermissions("petData:petBasicInfo:update")
    @ApiPermission(code = "petData:petBasicInfo:update", name = "更新宠物信息")
    public ResponseVO<?> update(@RequestBody PetBasicInfo petBasicInfo){
        petBasicInfoService.updatePet(petBasicInfo);

        return ResponseVO.success();
    }

    @GetMapping("/myPetPage")
    @RequiresPermissions("petData:petBasicInfo:myPetPage")
    @ApiPermission(code = "petData:petBasicInfo:myPetPage", name = "分页查询我的宠物信息")
    public ResponseVO<PageResult<PetBasicInfo>> myPetPage(PageQuery page, PetBasicInfo petBasicInfo){
        PageResult<PetBasicInfo> result = petBasicInfoService.getMyPetPage(page,petBasicInfo);

        return ResponseVO.success(result);
    }

    @RequiresPermissions("petData:petBasicInfo:myPetList")
    @ApiPermission(code = "petData:petBasicInfo:myPetList", name = "查询我的宠物信息列表")
    @GetMapping("/myPetList")
    public ResponseVO<List<PetBasicInfo>> myPetList(PetBasicInfo petBasicInfo){
        List<PetBasicInfo> result = petBasicInfoService.getMyPetList(petBasicInfo);

        return ResponseVO.success(result);
    }

    @RequiresPermissions("petData:petBasicInfo:delete")
    @ApiPermission(code = "petData:petBasicInfo:delete", name = "删除宠物信息")
    @DeleteMapping("/delete/{id}")
    public ResponseVO<?> delete(@PathVariable("id") String petId){
        petBasicInfoService.deleteById(petId);
        // TODO 删除其他关联表信息
        return ResponseVO.success();
    }

    @RequiresPermissions("petData:petBasicInfo:listSpecies")
    @ApiPermission(code = "petData:petBasicInfo:listSpecies", name = "查询宠物种类")
    @GetMapping("/listSpecies")
    public ResponseVO<List<PetSpecies>> list(PetSpecies petSpecies){
        Map<String, Object> params = new HashMap<>(BeanUtil.beanToMap(petSpecies));
        params.put("custom_order_by", "species_name");
        List<PetSpecies> result = petSpeciesService.selectAllList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("petData:petBasicInfo:listBreed")
    @ApiPermission(code = "petData:petBasicInfo:listBreed", name = "查询宠物种类")
    @GetMapping("/listBreed/{id}")
    public ResponseVO<List<PetBreed>> listBreed( @PathVariable("id") String speciesId){
        List<PetBreed> result = petBreedService.list(new LambdaQueryWrapper<PetBreed>().like(PetBreed::getSpeciesId, speciesId).orderByAsc(PetBreed::getBreedName));
        return ResponseVO.success(result);
    }

    @RequiresPermissions("petData:petBasicInfo:getBasicInfo")
    @ApiPermission(code = "petData:petBasicInfo:getBasicInfo", name = "查询宠物基础信息")
    @GetMapping("/getBasicInfo")
    public ResponseVO<PetBasicInfoVo> getBasicInfo() {
        PetBasicInfoVo vo = petBasicInfoService.getBasicInfo();
        return ResponseVO.success(vo);
    }
}
