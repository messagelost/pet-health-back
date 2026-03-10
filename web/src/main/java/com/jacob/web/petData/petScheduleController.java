package com.jacob.web.petData;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petData.dto.PetEventDto;
import com.jacob.common.model.petData.entity.PetEvent;
import com.jacob.common.model.petData.vo.PetScheduleVo;
import com.jacob.common.model.user.entity.SysUserSchedule;
import com.jacob.common.utils.JwtUtil;
import com.jacob.service.petData.PetEventService;
import com.jacob.service.user.SysUserScheduleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/petSchedule")
public class petScheduleController {

    @Autowired
    private PetEventService petEventService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SysUserScheduleService sysUserScheduleService;

    @PostMapping("/addEvent")
    @RequiresPermissions("petData:petSchedule:add")
    @ApiPermission(code = "petData:petSchedule:add", name = "添加宠物预约")
    public ResponseVO<?> addEvent(@RequestBody PetEventDto eventDto) {
        petEventService.addEvent(eventDto);
        return ResponseVO.success();
    }

    @PutMapping("/updateEvent")
    @RequiresPermissions("petData:petSchedule:update")
    @ApiPermission(code = "petData:petSchedule:update", name = "更新宠物预约")
    public ResponseVO<?> updateEvent(@RequestBody PetEvent event) {
        petEventService.updateEvent(event);
        return ResponseVO.success();
    }

    @GetMapping("/listEvent")
    @RequiresPermissions("petData:petSchedule:listEvent")
    @ApiPermission(code = "petData:petSchedule:listEvent", name = "查询宠物预约")
    public ResponseVO<List<PetEvent>> listEvent(PetEvent event) {
        // 查询当前用户
        event.setUserId(jwtUtil.getCurrentUserId());
        Map<String, Object> params = BeanUtil.beanToMap(event);
        List<PetEvent> list = petEventService.selectAllList(params);
        return ResponseVO.success(list);
    }

    @GetMapping("/listSchedule")
    @RequiresPermissions("petData:petSchedule:listSchedule")
    @ApiPermission(code = "petData:petSchedule:listSchedule", name = "查询日程")
    public ResponseVO<List<SysUserSchedule>> listSchedule(SysUserSchedule schedule) {
        // 查询当前用户
        schedule.setUserId(jwtUtil.getCurrentUserId());
        Map<String, Object> params = BeanUtil.beanToMap(schedule);
        List<SysUserSchedule> list = sysUserScheduleService.selectAllList(params);
        return ResponseVO.success(list);
    }

    @GetMapping("/listEventSchedule")
    @RequiresPermissions("petData:petSchedule:listEventSchedule")
    @ApiPermission(code = "petData:petSchedule:listEventSchedule", name = "查询预约和日程")
    public ResponseVO<PetScheduleVo> listEventSchedule(PetEvent event) {
        PetScheduleVo vo = new PetScheduleVo();
        vo.setEventList(petEventService.list(new LambdaQueryWrapper<PetEvent>().eq(PetEvent::getAppointmentTime, event.getAppointmentTime())));
        vo.setScheduleList(sysUserScheduleService.list(new LambdaQueryWrapper<SysUserSchedule>().eq(SysUserSchedule::getScheduleDate, event.getAppointmentTime())));

        return ResponseVO.success(vo);
    }
}
