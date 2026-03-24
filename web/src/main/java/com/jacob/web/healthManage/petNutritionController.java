package com.jacob.web.healthManage;

import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petConfig.entity.PetActivityCoefficient;
import com.jacob.common.model.petData.entity.PetBasicInfo;
import com.jacob.common.model.petData.entity.PetNutritionIntake;
import com.jacob.common.model.petData.entity.PetVaccineRecord;
import com.jacob.common.model.petData.vo.PetIntakeVo;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.petConfig.PetActivityCoefficientService;
import com.jacob.service.petData.PetBasicInfoService;
import com.jacob.service.petData.PetNutritionIntakeService;
import com.jacob.service.petData.PetWeightRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/petNutrition")
public class petNutritionController {

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PetNutritionIntakeService petNutritionIntakeService;
    @Autowired
    private PetActivityCoefficientService petActivityCoefficientService;
    @Autowired
    private PetBasicInfoService petBasicInfoService;
    @Autowired
    private PetWeightRecordService petWeightRecordService;

    @PostMapping("/add")
    @RequiresPermissions("health:petNutrition:add")
    @ApiPermission(code = "health:petNutrition:add", name = "添加摄入记录")
    public ResponseVO<?> add(@RequestBody PetNutritionIntake petNutritionIntake){
        petNutritionIntake.setIntakeId(snowflakeIdGenerator.generateIdWithPrefix("I"));
        petNutritionIntake.setCreateUserId(jwtUtil.getCurrentUserId());
        petNutritionIntake.setUpdateUserId(jwtUtil.getCurrentUserId());
        petNutritionIntakeService.insertWithBean(petNutritionIntake);

        return ResponseVO.success();
    }

    @PutMapping("/update")
    @RequiresPermissions("health:petNutrition:update")
    @ApiPermission(code = "health:petNutrition:update", name = "更新摄入记录")
    public ResponseVO<?> update(@RequestBody PetNutritionIntake petNutritionIntake){
        petNutritionIntake.setUpdateUserId(jwtUtil.getCurrentUserId());
        petNutritionIntakeService.updateWithBean(petNutritionIntake);

        return ResponseVO.success();
    }

    @GetMapping("/getInfo")
    @RequiresPermissions("health:petNutrition:getInfo")
    @ApiPermission(code = "health:petNutrition:getInfo", name = "获取摄入记录信息")
    public ResponseVO<PetIntakeVo> getInfo(){
        PetIntakeVo vo = new PetIntakeVo();
        List<PetBasicInfo> petList = petBasicInfoService.getMyPetList(new PetBasicInfo());
        petList.forEach(pet -> {
            BigDecimal val = petActivityCoefficientService.getPetActivityCoefficient(pet.getPetId(),pet.getBreedId());
            BigDecimal der = petWeightRecordService.getDer(pet.getPetId(), val);
        });

        return ResponseVO.success(vo);
    }
}
