package com.jacob.web.healthManage;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petConfig.entity.PetHealthIndicatorRule;
import com.jacob.common.model.petConfig.entity.PetLifeStage;
import com.jacob.common.model.petData.entity.PetBasicInfo;
import com.jacob.common.model.petData.entity.PetWeightRecord;
import com.jacob.common.model.petData.vo.PetScheduleVo;
import com.jacob.common.model.petData.vo.PetWeightVo;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.petConfig.PetHealthIndicatorRuleService;
import com.jacob.service.petConfig.PetLifeStageService;
import com.jacob.service.petData.PetBasicInfoService;
import com.jacob.service.petData.PetWeightRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/petWeight")
public class petWeightController {

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private PetWeightRecordService petWeightRecordService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PetBasicInfoService petBasicInfoService;
    @Autowired
    private PetHealthIndicatorRuleService petHealthIndicatorRuleService;
    @Autowired
    private PetLifeStageService petLifeStageService;

    @PostMapping("/add")
    @RequiresPermissions("health:petWeight:add")
    @ApiPermission(code = "health:petWeight:add", name = "添加体重记录")
    public ResponseVO<?> add(@RequestBody PetWeightRecord petWeightRecord){
        petWeightRecord.setRecordId(snowflakeIdGenerator.generateIdWithPrefix("R"));
        petWeightRecord.setCreateUserId(jwtUtil.getCurrentUserId());
        petWeightRecord.setUpdateUserId(jwtUtil.getCurrentUserId());
        petWeightRecordService.insertWithBean(petWeightRecord);

        return ResponseVO.success();
    }

    @GetMapping("/list")
    @RequiresPermissions("health:petWeight:list")
    @ApiPermission(code = "health:petWeight:list", name = "查询体重记录")
    public ResponseVO<PetWeightVo> list(){
        PetWeightVo vo = new PetWeightVo();
        String userId = jwtUtil.getCurrentUserId();

        PetBasicInfo pet = new PetBasicInfo();
        pet.setUserId(userId);
        Map<String, Object> params = BeanUtil.beanToMap(pet);
        params.put("joinLifeStage", "joinLifeStage");
        params.put("joinSpecies", "joinSpecies");
        List<PetBasicInfo> petList = petBasicInfoService.selectAllList(params);
        vo.setPetList(petList);

        List<String> petIds = petList.stream().map(PetBasicInfo::getPetId).toList();
        PetWeightRecord search = new PetWeightRecord();
        search.setPetIds(petIds);
        Map<String, Object> recordParams = BeanUtil.beanToMap(search);
        recordParams.put("custom_order_by", "create_time");
        List<PetWeightRecord> recordList = petWeightRecordService.selectAllList(recordParams);
        vo.setRecordList(recordList);

        return ResponseVO.success(vo);
    }
}
