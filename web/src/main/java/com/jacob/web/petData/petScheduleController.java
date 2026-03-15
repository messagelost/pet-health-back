package com.jacob.web.petData;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.annotation.ApiPermission;
import com.jacob.common.model.base.ResponseVO;
import com.jacob.common.model.petData.dto.PetEventDto;
import com.jacob.common.model.petData.entity.PetBasicInfo;
import com.jacob.common.model.petData.entity.PetEvent;
import com.jacob.common.model.petData.entity.PetEventReminder;
import com.jacob.common.model.petData.enums.EventStatusEnum;
import com.jacob.common.model.petData.enums.ReminderTypeEnum;
import com.jacob.common.model.petData.vo.PetScheduleVo;
import com.jacob.common.model.user.entity.SysUserSchedule;
import com.jacob.common.utils.JwtUtil;
import com.jacob.service.petData.PetBasicInfoService;
import com.jacob.service.petData.PetEventReminderService;
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
    @Autowired
    private PetBasicInfoService petBasicInfoService;
    @Autowired
    private PetEventReminderService petEventReminderService;

    @PostMapping("/addEvent")
    @RequiresPermissions("petData:petSchedule:add")
    @ApiPermission(code = "petData:petSchedule:add", name = "添加宠物预约")
    public ResponseVO<?> addEvent(@RequestBody PetEventDto eventDto) {
         PetEvent petEvent = petEventService.addEvent(eventDto);
         petEventReminderService.addReminder(eventDto, petEvent.getEventId());
        return ResponseVO.success();
    }

    @PutMapping("/updateEvent")
    @RequiresPermissions("petData:petSchedule:update")
    @ApiPermission(code = "petData:petSchedule:update", name = "更新宠物预约")
    public ResponseVO<?> updateEvent(@RequestBody PetEventDto eventDto) {
        PetEvent event = petEventService.getById(eventDto.getEventId());
        event.setEventType(eventDto.getEventType());
        event.setEventContent(eventDto.getEventContent());
        event.setAppointmentTime(eventDto.getAppointmentTime());
        petEventService.updateEvent(event);
        petEventReminderService.updateReminder(eventDto);
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
        String userId = jwtUtil.getCurrentUserId();

        event.setStatus(EventStatusEnum.NOT_REMIND.getCode());
        event.setUserId(userId);
        vo.setEventList(petEventService.selectAllList(BeanUtil.beanToMap(event)));

        PetBasicInfo pet = new PetBasicInfo();
        pet.setUserId(userId);
        Map<String, Object> params = BeanUtil.beanToMap(pet);
        params.put("joinLifeStage", "joinLifeStage");
        vo.setPetList(petBasicInfoService.selectAllList(params));

        PetEventReminder reminder = new PetEventReminder();
        reminder.setUserId(userId);
        reminder.setRemindType(ReminderTypeEnum.REPEAT.getCode());
        vo.setScheduleList(petEventReminderService.selectAllList(BeanUtil.beanToMap(reminder)));

        List<String> result = petEventService.list(
                new LambdaQueryWrapper<PetEvent>()
                        .eq(PetEvent::getUserId, jwtUtil.getCurrentUserId())
                        .eq(PetEvent::getStatus, EventStatusEnum.NOT_REMIND.getCode())
        ).stream().map( p -> DateUtil.format(p.getAppointmentTime(), "yyyy-MM-dd")).toList();
        vo.setEventDates(result);

        return ResponseVO.success(vo);
    }

    @GetMapping("/listEventByDate")
    @RequiresPermissions("petData:petSchedule:listEventByDate")
    @ApiPermission(code = "petData:petSchedule:listEventByDate", name = "根据年月查询预约")
    public ResponseVO<List<PetEvent>> listEventByDate(@RequestParam String date) {
        List<PetEvent> result = petEventService.list(
                new LambdaQueryWrapper<PetEvent>()
                        .eq(PetEvent::getUserId, jwtUtil.getCurrentUserId())
                        .eq(PetEvent::getStatus, EventStatusEnum.NOT_REMIND.getCode())
                        .like(PetEvent::getAppointmentTime, date)
        );

        return ResponseVO.success(result);
    }

    @GetMapping("/getEventById/{id}")
    @RequiresPermissions("petData:petSchedule:getEventById")
    @ApiPermission(code = "petData:petSchedule:getEventById", name = "根据主键查询预约")
    public ResponseVO<PetEvent> getEventById(@PathVariable String id) {
        PetEvent event = petEventService.getById(id);
        return ResponseVO.success(event);
    }

    @GetMapping("/getReminderByEvent/{id}")
    @RequiresPermissions("petData:petSchedule:getReminderByEvent")
    @ApiPermission(code = "petData:petSchedule:getReminderByEvent", name = "根据主键查询提醒")
    public ResponseVO<PetEventReminder> getReminderByEvent(@PathVariable String id) {
        PetEventReminder reminder = petEventReminderService.getOne(new LambdaQueryWrapper<PetEventReminder>().eq(PetEventReminder::getEventId, id));
        return ResponseVO.success(reminder);
    }

    @DeleteMapping("/deleteEvent/{id}")
    @RequiresPermissions("petData:petSchedule:deleteEvent")
    @ApiPermission(code = "petData:petSchedule:deleteEvent", name = "根据主键删除预约")
    public ResponseVO<?> deleteEvent(@PathVariable String id) {
        // 删除事件
        petEventService.deleteById(id);
        // 删除事件提醒
        petEventReminderService.deleteReminder(id);
        return ResponseVO.success();
    }
}
