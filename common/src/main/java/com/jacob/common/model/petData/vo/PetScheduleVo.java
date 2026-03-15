package com.jacob.common.model.petData.vo;


import com.jacob.common.model.petData.entity.PetBasicInfo;
import com.jacob.common.model.petData.entity.PetEvent;
import com.jacob.common.model.user.entity.SysUserSchedule;
import lombok.Data;

import java.util.List;

@Data
public class PetScheduleVo {

    private List<PetBasicInfo> petList;

    private List<PetEvent> eventList;

    private List<SysUserSchedule> scheduleList;

    private List<String> eventDates;

    // TODO 日程建议
}
