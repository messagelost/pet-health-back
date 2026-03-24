package com.jacob.common.model.petData.vo;

import com.jacob.common.model.petConfig.entity.VaccineInfo;
import com.jacob.common.model.petData.entity.PetBasicInfo;
import com.jacob.common.model.petData.entity.PetEvent;
import com.jacob.common.model.petData.entity.PetVaccineRecord;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PetVaccineVo {

    /**
     * 宠物
     */
    List<PetBasicInfo> petList;

    /**
     * 疫苗记录
     */
    List<PetVaccineRecord> recordList;

    /**
     * 预约
     */
    List<PetEvent> eventList;

    /**
     * 建议疫苗
     * 1、未接种的疫苗
     * 2、已接种但次数未满的疫苗
     * 3、已接种但有加强针未打的疫苗
     */
    Map<String, List<VaccineInfo>> suggestListMap;

    /**
     * 覆盖率
     */
    Map<String, List<VaccineCoverVo>> vaccineCover;

}
