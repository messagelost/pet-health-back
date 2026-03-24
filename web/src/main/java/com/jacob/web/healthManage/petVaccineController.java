package com.jacob.web.healthManage;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petConfig.entity.VaccineInfo;
import com.jacob.common.model.petData.entity.PetBasicInfo;
import com.jacob.common.model.petData.entity.PetEvent;
import com.jacob.common.model.petData.entity.PetVaccineRecord;
import com.jacob.common.model.petData.enums.EventTypeEnum;
import com.jacob.common.model.petData.vo.PetVaccineVo;
import com.jacob.common.model.petData.vo.VaccineCoverVo;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.petConfig.VaccineInfoService;
import com.jacob.service.petData.PetBasicInfoService;
import com.jacob.service.petData.PetEventService;
import com.jacob.service.petData.PetVaccineRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/petVaccine")
public class petVaccineController {

    @Autowired
    private PetVaccineRecordService petVaccineRecordService;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PetBasicInfoService petBasicInfoService;
    @Autowired
    private PetEventService petEventService;
    @Autowired
    private VaccineInfoService vaccineInfoService;

    @PostMapping("/add")
    @RequiresPermissions("health:petVaccine:add")
    @ApiPermission(code = "health:petVaccine:add", name = "添加疫苗记录")
    public ResponseVO<?> add(@RequestBody PetVaccineRecord petVaccineRecord){
        petVaccineRecord.setRecordId(snowflakeIdGenerator.generateIdWithPrefix("R"));
        petVaccineRecord.setCreateUserId(jwtUtil.getCurrentUserId());
        petVaccineRecord.setUpdateUserId(jwtUtil.getCurrentUserId());
        petVaccineRecordService.insertWithBean(petVaccineRecord);

        return ResponseVO.success();
    }

    @PutMapping("/update")
    @RequiresPermissions("health:petVaccine:update")
    @ApiPermission(code = "health:petVaccine:update", name = "更新疫苗记录")
    public ResponseVO<?> update(@RequestBody PetVaccineRecord petVaccineRecord){
        petVaccineRecord.setUpdateUserId(jwtUtil.getCurrentUserId());
        petVaccineRecordService.updateWithBean(petVaccineRecord);

        return ResponseVO.success();
    }

    @GetMapping("/getInfo")
    @RequiresPermissions("health:petVaccine:getInfo")
    @ApiPermission(code = "health:petVaccine:getInfo", name = "查询疫苗记录")
    public ResponseVO<PetVaccineVo> getInfo(){

        String userId = jwtUtil.getCurrentUserId();
        PetVaccineVo vo = new PetVaccineVo();

        // 用户宠物
        List<PetBasicInfo> petList = petBasicInfoService.getMyPetList(new PetBasicInfo());
        vo.setPetList(petList);
        List<String> idList = petList.stream().map(PetBasicInfo::getPetId).toList();

        // 疫苗记录
        Map<String, Object> params = BeanUtil.beanToMap(new PetVaccineRecord());
        params.put("petIds", idList);
        params.put("joinPet", "joinPet");
        params.put("joinBreed", "joinBreed");
        params.put("joinVaccine", "joinVaccine");
        params.put("custom_order_by", "create_time");
        List<PetVaccineRecord> recordList = petVaccineRecordService.selectAllList( params );
        vo.setRecordList(recordList);

        // 预约记录
        PetEvent search = new PetEvent();
        search.setUserId(userId);
        search.setEventType(EventTypeEnum.VACCINE.getCode());
        List<PetEvent> eventList = petEventService.selectAllList(BeanUtil.beanToMap(search));
        vo.setEventList(eventList);

        Map<String, List<VaccineInfo>> suggests = new HashMap<>();
        Map<String, List<VaccineCoverVo>> covers = new HashMap<>();
        petList.forEach(pet -> {
            Map<String, Object> vp = BeanUtil.beanToMap(pet);
            List<VaccineInfo> suggestList = vaccineInfoService.selectSuggestVaccine(vp);
            List<VaccineCoverVo> coverList = vaccineInfoService.getCover(vp);
            suggests.put(pet.getPetId(), suggestList);
            covers.put(pet.getPetId(), coverList);
        });
        vo.setSuggestListMap(suggests);
        vo.setVaccineCover(covers);

        return ResponseVO.success(vo);
    }

    @GetMapping("/listVaccine")
    @RequiresPermissions("health:petVaccine:listVaccine")
    @ApiPermission(code = "health:petVaccine:listVaccine", name = "查询疫苗信息")
    public ResponseVO<List<VaccineInfo>> listVaccine(VaccineInfo info) {
        Map<String, Object> params = BeanUtil.beanToMap(info);
        List<VaccineInfo> result = vaccineInfoService.selectAllList(params);
        return ResponseVO.success(result);
    }
}
