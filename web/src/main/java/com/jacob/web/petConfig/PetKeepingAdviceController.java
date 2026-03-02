package com.jacob.web.petConfig;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petConfig.entity.PetBreed;
import com.jacob.common.model.petConfig.entity.PetLifeStage;
import com.jacob.common.model.petConfig.entity.PetKeepingAdvice;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.service.petConfig.PetKeepingAdviceService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/petKeepingAdvice")
public class PetKeepingAdviceController {

    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private PetKeepingAdviceService petKeepingAdviceService;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @RequiresPermissions("petConfig:petKeepingAdvice:add")
    @ApiPermission(code = "petConfig:petKeepingAdvice:add", name = "添加宠物饲养建议")
    public ResponseVO<?> add(@RequestBody PetKeepingAdvice petKeepingAdvice){
        petKeepingAdvice.setAdviceId(snowflakeIdGenerator.generateIdWithPrefix("A"));
        petKeepingAdvice.setCreateUserId(jwtUtil.getCurrentUserId());
        petKeepingAdvice.setUpdateUserId(jwtUtil.getCurrentUserId());
        petKeepingAdviceService.insertWithBean(petKeepingAdvice);

        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petKeepingAdvice:update")
    @ApiPermission(code = "petConfig:petKeepingAdvice:update", name = "更新宠物饲养建议")
    @PutMapping("/{id}")
    public ResponseVO<?> update(@PathVariable("id") String adviceId, @RequestBody PetKeepingAdvice petKeepingAdvice){
        petKeepingAdvice.setAdviceId(adviceId);
        petKeepingAdvice.setUpdateUserId(jwtUtil.getCurrentUserId());
        petKeepingAdviceService.updateWithBean(petKeepingAdvice);

        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petKeepingAdvice:delete")
    @ApiPermission(code = "petConfig:petKeepingAdvice:delete", name = "删除宠物饲养建议")
    @DeleteMapping("/{id}")
    public ResponseVO<?> delete(@PathVariable("id") String adviceId){
        petKeepingAdviceService.deleteById(adviceId);
        return ResponseVO.success();
    }

    @RequiresPermissions("petConfig:petKeepingAdvice:page")
    @ApiPermission(code = "petConfig:petKeepingAdvice:page", name = "分页查询宠物饲养建议")
    @GetMapping("/page")
    public ResponseVO<PageResult<PetKeepingAdvice>> page(PageQuery page, PetKeepingAdvice petKeepingAdvice){
        Map<String, Object> params = BeanUtil.beanToMap(page);
        params.putAll(BeanUtil.beanToMap(petKeepingAdvice));
        PageResult<PetKeepingAdvice> result = petKeepingAdviceService.selectPageList(params);
        return ResponseVO.success(result);
    }

    @RequiresPermissions("petConfig:petKeepingAdvice:list")
    @ApiPermission(code = "petConfig:petKeepingAdvice:list", name = "查询宠物饲养建议")
    @GetMapping("/list")
    public ResponseVO<List<PetKeepingAdvice>> list(PetKeepingAdvice petKeepingAdvice){
        Map<String, Object> params = new HashMap<>(BeanUtil.beanToMap(petKeepingAdvice));
        List<PetKeepingAdvice> result = petKeepingAdviceService.selectAllList(params);
        return ResponseVO.success(result);
    }


}
