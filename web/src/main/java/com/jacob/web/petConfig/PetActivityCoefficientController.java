package com.jacob.web.petConfig;

import cn.hutool.core.bean.BeanUtil;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petConfig.entity.PetActivityCoefficient;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.petConfig.PetActivityCoefficientService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/petActivityCoefficient")
public class PetActivityCoefficientController {

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PetActivityCoefficientService petActivityCoefficientService;

    @PostMapping
    @RequiresPermissions("petConfig:petActivityCoefficient:add")
    @ApiPermission(code = "petConfig:petActivityCoefficient:add", name = "添加活动系数")
    public ResponseVO<?> add(@RequestBody PetActivityCoefficient petActivityCoefficient){
        petActivityCoefficient.setCoefficientId(snowflakeIdGenerator.generateIdWithPrefix("C"));
        petActivityCoefficient.setCreateUserId(jwtUtil.getCurrentUserId());
        petActivityCoefficient.setUpdateUserId(jwtUtil.getCurrentUserId());
        petActivityCoefficientService.insertWithBean(petActivityCoefficient);

        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petActivityCoefficient:update")
    @ApiPermission(code = "petConfig:petActivityCoefficient:update", name = "更新活动系数")
    @PutMapping("/{id}")
    public ResponseVO<?> update(@PathVariable("id") String coefficientId, @RequestBody PetActivityCoefficient petActivityCoefficient){
        petActivityCoefficient.setCoefficientId(coefficientId);
        petActivityCoefficient.setUpdateUserId(jwtUtil.getCurrentUserId());
        petActivityCoefficientService.updateWithBean(petActivityCoefficient);

        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petActivityCoefficient:delete")
    @ApiPermission(code = "petConfig:petActivityCoefficient:delete", name = "删除活动系数")
    @DeleteMapping("/{id}")
    public ResponseVO<?> delete(@PathVariable("id") String coefficientId){
        petActivityCoefficientService.deleteById(coefficientId);
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petActivityCoefficient:page")
    @ApiPermission(code = "petConfig:petActivityCoefficient:page", name = "分页查询活动系数")
    @GetMapping("/page")
    public ResponseVO<PageResult<PetActivityCoefficient>> page(PageQuery page, PetActivityCoefficient petActivityCoefficient){
        Map<String, Object> params = BeanUtil.beanToMap(page);
        params.putAll(BeanUtil.beanToMap(petActivityCoefficient));
        params.put("custom_order_by","breed_ids,calculate_priority");
        PageResult<PetActivityCoefficient> result = petActivityCoefficientService.selectPageList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("petConfig:petActivityCoefficient:list")
    @ApiPermission(code = "petConfig:petActivityCoefficient:list", name = "查询活动系数")
    @GetMapping("/list")
    public ResponseVO<List<PetActivityCoefficient>> list(PetActivityCoefficient petActivityCoefficient){
        Map<String, Object> params = new HashMap<>(BeanUtil.beanToMap(petActivityCoefficient));
        List<PetActivityCoefficient> result = petActivityCoefficientService.selectAllList(params);
        return ResponseVO.success(result);
    }
    
}
