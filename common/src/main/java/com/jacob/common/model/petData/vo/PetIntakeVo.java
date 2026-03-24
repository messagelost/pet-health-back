package com.jacob.common.model.petData.vo;

import com.jacob.common.model.petData.entity.PetBasicInfo;
import lombok.Data;

import java.util.List;

@Data
public class PetIntakeVo {
    private List<PetBasicInfo> petList;
}
