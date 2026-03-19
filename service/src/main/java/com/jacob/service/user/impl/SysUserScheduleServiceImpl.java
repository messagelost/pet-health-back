package com.jacob.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.model.petData.entity.PetEvent;
import com.jacob.common.model.user.entity.SysUserSchedule;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.user.SysUserScheduleDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petData.PetEventService;
import com.jacob.service.user.SysUserScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SysUserScheduleServiceImpl extends BaseServiceImpl<SysUserScheduleDao, SysUserSchedule> implements SysUserScheduleService {

    @Autowired
    private SysUserScheduleDao sysUserScheduleDao;
    @Autowired
    private PetEventService petEventService;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;

    @Override
    public SqlDao getDao() {
        return sysUserScheduleDao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addScheduleByScan() {
        log.info("===== 开始执行：次日日程生成任务 =====");
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LambdaQueryWrapper<PetEvent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(PetEvent::getStatus, 0)
                .ge(PetEvent::getAppointmentTime, tomorrow.atStartOfDay())
                .lt(PetEvent::getAppointmentTime, tomorrow.plusDays(1).atStartOfDay());

        List<PetEvent> eventList = petEventService.list(queryWrapper);
        if (eventList.isEmpty()) {
            log.info("===== 次日无待生成日程，任务结束 =====");
            return;
        }
        List<SysUserSchedule> scheduleList = new ArrayList<>();
        for (PetEvent event : eventList) {
            SysUserSchedule schedule = new SysUserSchedule();
            schedule.setScheduleId(snowflakeIdGenerator.generateIdWithPrefix("S"));
            schedule.setUserId(event.getUserId());
            schedule.setPetId(event.getPetId());
            schedule.setScheduleDate(tomorrow);
            schedule.setScheduleTime(event.getAppointmentTime().toLocalTime());
            schedule.setContent(event.getEventContent());
            schedule.setScheduleType(event.getEventType());
            schedule.setStatus(0); // 0=未完成
            scheduleList.add(schedule);
        }

        boolean success = saveBatch(scheduleList);
        log.info("===== 次日日程生成共：{} 条 结果{} =====", scheduleList.size(), success);
    }
}
