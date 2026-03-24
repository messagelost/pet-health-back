package com.jacob.common.model.petData.vo;

import lombok.Data;

@Data
public class VaccineCoverVo {

    private String vaccineId;
    private String vaccineName;
    private Integer requiredTimes;
    private Integer actualTimes;
    private Integer isCompleted;
    private Integer missingTimes;

}
