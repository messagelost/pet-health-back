package com.jacob.web.healthManage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petConfig.entity.PetActivityCoefficient;
import com.jacob.common.model.petData.dto.NutrientDto;
import com.jacob.common.model.petData.entity.PetBasicInfo;
import com.jacob.common.model.petData.entity.PetNutritionIntake;
import com.jacob.common.model.petData.entity.PetVaccineRecord;
import com.jacob.common.model.petData.vo.PetIntakeVo;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.OrcUtils;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.petConfig.PetActivityCoefficientService;
import com.jacob.service.petData.PetBasicInfoService;
import com.jacob.service.petData.PetNutritionIntakeService;
import com.jacob.service.petData.PetWeightRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        vo.setPetList(petList);
        petList.forEach(pet -> {
            BigDecimal val = petActivityCoefficientService.getPetActivityCoefficient(pet.getPetId(),pet.getBreedId());
            BigDecimal der = petWeightRecordService.getDer(pet.getPetId(), val);
        });

        return ResponseVO.success(vo);
    }

    @PostMapping("/ocr")
    @RequiresPermissions("health:petNutrition:ocr")
    @ApiPermission(code = "health:petNutrition:ocr", name = "扫描图片提取营养成分")
    public ResponseVO<List<NutrientDto>> ocrScan(@RequestParam("file") MultipartFile file) throws IOException {
        JSONObject jsonObject = OrcUtils.ocrRegularScan(file);
        List<NutrientDto> nList = new ArrayList<>();
        JSONArray arr = jsonObject.getJSONArray("words_result");
        for (int i = 0; i < arr.size(); i++) {
            String text = arr.getJSONObject(i).getString("words");
            // 找到百分比
            if (text.contains("%")) {
                String percent = text.replaceAll("[^0-9.]", "");
                // 向前找营养名称
                for (int j = i - 1; j >= 0 && j >= i - 3; j--) {
                    String name = arr.getJSONObject(j).getString("words");
                    if (petActivityCoefficientService.isNutrition(name)) {
                        NutrientDto dto = new NutrientDto(name,  percent);
                        nList.add(dto);
                        break;
                    }
                }
            }
        }
        log.info("识别结果：{}", nList);
        return ResponseVO.success(nList);
    }
}
