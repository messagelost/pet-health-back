package com.jacob.web.petConfig;

import cn.hutool.core.bean.BeanUtil;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petConfig.entity.PetKeepingAdvice;
import com.jacob.common.model.petConfig.entity.VaccineInfo;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.petConfig.VaccineInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vaccineInfo")
public class VaccineInfoController {

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private VaccineInfoService vaccineInfoService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @RequiresPermissions("petConfig:vaccineInfo:add")
    @ApiPermission(code = "petConfig:vaccineInfo:add", name = "添加疫苗信息")
    public ResponseVO<?> add(@RequestBody VaccineInfo vaccineInfo){
        vaccineInfo.setVaccineId(snowflakeIdGenerator.generateIdWithPrefix("V"));
        vaccineInfo.setCreateUserId(jwtUtil.getCurrentUserId());
        vaccineInfo.setUpdateUserId(jwtUtil.getCurrentUserId());
        vaccineInfoService.insertWithBean(vaccineInfo);

        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:vaccineInfo:update")
    @ApiPermission(code = "petConfig:vaccineInfo:update", name = "更新疫苗信息")
    @PutMapping("/{id}")
    public ResponseVO<?> update(@PathVariable("id") String vaccineId, @RequestBody VaccineInfo vaccineInfo){
        vaccineInfo.setVaccineId(vaccineId);
        vaccineInfo.setUpdateUserId(jwtUtil.getCurrentUserId());
        vaccineInfoService.updateWithBean(vaccineInfo);

        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:vaccineInfo:delete")
    @ApiPermission(code = "petConfig:vaccineInfo:delete", name = "删除疫苗信息")
    @DeleteMapping("/{id}")
    public ResponseVO<?> delete(@PathVariable("id") String vaccineId){
        vaccineInfoService.deleteById(vaccineId);
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:vaccineInfo:page")
    @ApiPermission(code = "petConfig:vaccineInfo:page", name = "分页查询疫苗信息")
    @GetMapping("/page")
    public ResponseVO<PageResult<VaccineInfo>> page(PageQuery page, VaccineInfo vaccineInfo){
        Map<String, Object> params = BeanUtil.beanToMap(page);
        params.putAll(BeanUtil.beanToMap(vaccineInfo));
        PageResult<VaccineInfo> result = vaccineInfoService.selectPageList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("petConfig:vaccineInfo:list")
    @ApiPermission(code = "petConfig:vaccineInfo:list", name = "查询宠物疫苗信息")
    @GetMapping("/list")
    public ResponseVO<List<VaccineInfo>> list(VaccineInfo vaccineInfo){
        Map<String, Object> params = new HashMap<>(BeanUtil.beanToMap(vaccineInfo));
        List<VaccineInfo> result = vaccineInfoService.selectAllList(params);
        return ResponseVO.success(result);
    }
}
