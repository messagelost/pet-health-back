package com.jacob.web.petConfig;

import cn.hutool.core.bean.BeanUtil;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petConfig.entity.PetHealthIndicator;
import com.jacob.common.model.petConfig.entity.PetHealthIndicatorRule;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.petConfig.PetHealthIndicatorRuleService;
import com.jacob.service.petConfig.PetHealthIndicatorService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/petIndicatorRule")
public class PetIndicatorRuleController {

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PetHealthIndicatorRuleService petHealthIndicatorRuleService;

    @PostMapping
    @RequiresPermissions("petConfig:petIndicatorRule:add")
    @ApiPermission(code = "petConfig:petIndicatorRule:add", name = "添加指标适用规则")
    public ResponseVO<?> add(@RequestBody PetHealthIndicatorRule petIndicatorRule){
        petIndicatorRule.setRuleId(snowflakeIdGenerator.generateIdWithPrefix("I"));
        petIndicatorRule.setCreateUserId(jwtUtil.getCurrentUserId());
        petIndicatorRule.setUpdateUserId(jwtUtil.getCurrentUserId());
        petHealthIndicatorRuleService.insertWithBean(petIndicatorRule);
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petIndicatorRule:update")
    @ApiPermission(code = "petConfig:petIndicatorRule:update", name = "更新指标适用规则")
    @PutMapping("/{id}")
    public ResponseVO<?> update(@PathVariable("id") String ruleId, @RequestBody PetHealthIndicatorRule petIndicatorRule){
        petIndicatorRule.setRuleId(ruleId);
        petIndicatorRule.setUpdateUserId(jwtUtil.getCurrentUserId());
        petHealthIndicatorRuleService.updateWithBean(petIndicatorRule);
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petIndicatorRule:delete")
    @ApiPermission(code = "petConfig:petIndicatorRule:delete", name = "删除指标适用规则")
    @DeleteMapping("/{id}")
    public ResponseVO<?> delete(@PathVariable("id") String ruleId){
        petHealthIndicatorRuleService.deleteById(ruleId);
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petIndicatorRule:page")
    @ApiPermission(code = "petConfig:petIndicatorRule:page", name = "分页查询指标适用规则")
    @GetMapping("/page")
    public ResponseVO<PageResult<PetHealthIndicatorRule>> page(PageQuery page, PetHealthIndicatorRule petIndicatorRule){
        Map<String, Object> params = BeanUtil.beanToMap(page);
        params.putAll(BeanUtil.beanToMap(petIndicatorRule));
        PageResult<PetHealthIndicatorRule> result = petHealthIndicatorRuleService.selectPageList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("petConfig:petIndicatorRule:list")
    @ApiPermission(code = "petConfig:petIndicatorRule:list", name = "查询指标适用规则")
    @GetMapping("/list")
    public ResponseVO<List<PetHealthIndicatorRule>> list(PetHealthIndicatorRule petIndicatorRule){
        Map<String, Object> params = new HashMap<>(BeanUtil.beanToMap(petIndicatorRule));
        List<PetHealthIndicatorRule> result = petHealthIndicatorRuleService.selectAllList(params);
        return ResponseVO.success(result);
    }
}
