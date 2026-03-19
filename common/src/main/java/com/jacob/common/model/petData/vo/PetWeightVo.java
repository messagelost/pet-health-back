package com.jacob.common.model.petData.vo;

import com.jacob.common.model.petConfig.entity.PetHealthIndicatorRule;
import com.jacob.common.model.petData.entity.PetBasicInfo;
import com.jacob.common.model.petData.entity.PetWeightRecord;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PetWeightVo {

    private List<PetBasicInfo> petList;

    private List<PetWeightRecord> recordList;

}
