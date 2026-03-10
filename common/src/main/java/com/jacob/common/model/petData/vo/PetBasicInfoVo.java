package com.jacob.common.model.petData.vo;

import com.jacob.common.model.petData.entity.PetBasicInfo;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class PetBasicInfoVo {

    /**
     * 宠物基础信息列表
     */
    private List<PetBasicInfo> petBasicInfoList;

    // TODO 健康概况

    // TODO 今日日程
}
