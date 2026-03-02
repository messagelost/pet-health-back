package com.jacob.web.petConfig;

import cn.hutool.core.bean.BeanUtil;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petConfig.entity.DewormingMedicineInfo;
import com.jacob.common.model.petConfig.entity.VaccineInfo;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.petConfig.DewormingMedicineInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dewormingInfo")
public class DewormingInfoController {

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private DewormingMedicineInfoService dewormingMedicineInfoService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @RequiresPermissions("petConfig:dewormingInfo:add")
    @ApiPermission(code = "petConfig:dewormingInfo:add", name = "添加疫苗信息")
    public ResponseVO<?> add(@RequestBody DewormingMedicineInfo dewormingMedicineInfo){
        dewormingMedicineInfo.setMedicineId(snowflakeIdGenerator.generateIdWithPrefix("D"));
        dewormingMedicineInfo.setCreateUserId(jwtUtil.getCurrentUserId());
        dewormingMedicineInfo.setUpdateUserId(jwtUtil.getCurrentUserId());
        dewormingMedicineInfoService.insertWithBean(dewormingMedicineInfo);

        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:dewormingInfo:update")
    @ApiPermission(code = "petConfig:dewormingInfo:update", name = "更新疫苗信息")
    @PutMapping("/{id}")
    public ResponseVO<?> update(@PathVariable("id") String medicineId, @RequestBody DewormingMedicineInfo dewormingMedicineInfo){
        dewormingMedicineInfo.setMedicineId(medicineId);
        dewormingMedicineInfo.setUpdateUserId(jwtUtil.getCurrentUserId());
        dewormingMedicineInfoService.updateWithBean(dewormingMedicineInfo);

        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:dewormingInfo:delete")
    @ApiPermission(code = "petConfig:dewormingInfo:delete", name = "删除疫苗信息")
    @DeleteMapping("/{id}")
    public ResponseVO<?> delete(@PathVariable("id") String medicineId){
        dewormingMedicineInfoService.deleteById(medicineId);
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:dewormingInfo:page")
    @ApiPermission(code = "petConfig:dewormingInfo:page", name = "分页查询疫苗信息")
    @GetMapping("/page")
    public ResponseVO<PageResult<DewormingMedicineInfo>> page(PageQuery page, DewormingMedicineInfo dewormingMedicineInfo){
        Map<String, Object> params = BeanUtil.beanToMap(page);
        params.putAll(BeanUtil.beanToMap(dewormingMedicineInfo));
        PageResult<DewormingMedicineInfo> result = dewormingMedicineInfoService.selectPageList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("petConfig:dewormingInfo:list")
    @ApiPermission(code = "petConfig:dewormingInfo:list", name = "查询宠物疫苗信息")
    @GetMapping("/list")
    public ResponseVO<List<DewormingMedicineInfo>> list(DewormingMedicineInfo dewormingMedicineInfo){
        Map<String, Object> params = new HashMap<>(BeanUtil.beanToMap(dewormingMedicineInfo));
        List<DewormingMedicineInfo> result = dewormingMedicineInfoService.selectAllList(params);
        return ResponseVO.success(result);
    }
}
