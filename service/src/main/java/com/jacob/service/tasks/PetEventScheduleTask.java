package com.jacob.service.tasks;


import com.jacob.service.user.SysUserScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 定时任务：
 * 每天 23:00 扫描 pet_event 表，将【次日】的预约事件生成到 sys_user_schedule
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PetEventScheduleTask {

    @Autowired
    private SysUserScheduleService sysUserScheduleService;

    @Scheduled(cron = "0 0 23 * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void generateNextDaySchedule() {
        sysUserScheduleService.addScheduleByScan();
    }
}
