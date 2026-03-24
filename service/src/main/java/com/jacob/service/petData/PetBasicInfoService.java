package com.jacob.service.petData;

import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.petConfig.entity.VaccineInfo;
import com.jacob.common.model.petData.entity.PetBasicInfo;
import com.jacob.common.model.petData.vo.PetBasicInfoVo;
import com.jacob.service.base.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PetBasicInfoService extends BaseService<PetBasicInfo> {
    /**
     * 添加宠物
     * @param petBasicInfo 宠物信息
     */
    void addPet(PetBasicInfo petBasicInfo);

    /**
     * 修改宠物
     * @param petBasicInfo 宠物信息
     */
    void updatePet(PetBasicInfo petBasicInfo);

    /**
     * 计算宠物生命周期阶段
     * @param petBasicInfo 宠物信息
     * @return 宠物信息
     */
    PetBasicInfo calcLifeStage(PetBasicInfo petBasicInfo);

    /**
     * 查询我的宠物
     * @param page 分页信息
     * @param petBasicInfo 筛选条件
     * @return 分页结果
     */
    PageResult<PetBasicInfo> getMyPetPage(PageQuery page, PetBasicInfo petBasicInfo);

    /**
     * 查询我的宠物
     * @param petBasicInfo 筛选条件
     * @return 列表
     */
    List<PetBasicInfo> getMyPetList(PetBasicInfo petBasicInfo);

    /**
     * 获取宠物基础信息
     * @return  基础信息VO
     */
    PetBasicInfoVo getBasicInfo();

    /**
     * 根据用户ID更新宠物信息
     * @param userId 用户ID
     */
    @Transactional(rollbackFor = Exception.class)
    void updatePetByUserId(String userId);
}
