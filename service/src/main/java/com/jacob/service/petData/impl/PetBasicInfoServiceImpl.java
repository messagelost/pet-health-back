package com.jacob.service.petData.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jacob.common.model.base.PageQuery;
import com.jacob.common.model.base.PageResult;
import com.jacob.common.model.petConfig.entity.PetLifeStage;
import com.jacob.common.model.petConfig.entity.PetSpecies;
import com.jacob.common.model.petData.entity.PetBasicInfo;
import com.jacob.common.model.petData.entity.PetWeightRecord;
import com.jacob.common.model.petData.vo.PetBasicInfoVo;
import com.jacob.common.utils.JwtUtil;
import com.jacob.common.utils.SnowflakeIdGenerator;
import com.jacob.dao.base.SqlDao;
import com.jacob.dao.mappers.petData.PetBasicInfoDao;
import com.jacob.service.base.impl.BaseServiceImpl;
import com.jacob.service.petConfig.PetLifeStageService;
import com.jacob.service.petConfig.PetSpeciesService;
import com.jacob.service.petData.PetBasicInfoService;
import com.jacob.service.petData.PetWeightRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PetBasicInfoServiceImpl extends BaseServiceImpl<PetBasicInfoDao, PetBasicInfo> implements PetBasicInfoService {

    @Autowired
    private PetBasicInfoDao petBasicInfoDao;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PetSpeciesService petSpeciesService;
    @Autowired
    private PetLifeStageService petLifeStageService;
    @Autowired
    private PetWeightRecordService petWeightRecordService;

    @Override
    public SqlDao getDao() {
        return petBasicInfoDao;
    }

    @Override
    public void addPet(PetBasicInfo petBasicInfo) {
        String userId = jwtUtil.getCurrentUserId();
        petBasicInfo.setPetId(snowflakeIdGenerator.generateIdWithPrefix("P"));
        petBasicInfo.setUserId(userId);
        petBasicInfo.setCreateUserId(userId);
        petBasicInfo.setUpdateUserId(userId);

        // 计算生命周期
        petBasicInfo = calcLifeStage(petBasicInfo);

        insertWithBean(petBasicInfo);

        // 添加重量记录
        PetWeightRecord petWeightRecord = new PetWeightRecord();
        petWeightRecord.setRecordId(snowflakeIdGenerator.generateIdWithPrefix("PW"));
        petWeightRecord.setPetId(petBasicInfo.getPetId());
        petWeightRecord.setWeight(petBasicInfo.getPetWeight());
        petWeightRecord.setLifeStage(petBasicInfo.getLifeStage());
        petWeightRecord.setCreateUserId(userId);
        petWeightRecord.setUpdateUserId(userId);
        petWeightRecordService.insertWithBean(petWeightRecord);
    }

    @Override
    public PetBasicInfo calcLifeStage(PetBasicInfo petBasicInfo) {
        LocalDate petBirthday = petBasicInfo.getPetBirthday();
        int petAgeInMonths = 0;
        if(petBirthday != null){
            LocalDate now = LocalDate.now();
            // 计算年份差和月份差
            int yearsDiff = now.getYear() - petBirthday.getYear();
            int monthsDiff = now.getMonthValue() - petBirthday.getMonthValue();
            // 总月数 = 年份差 × 12 + 月份差
            petAgeInMonths = yearsDiff * 12 + monthsDiff;
            // 如果当前日期在生日之前，需要减去 1 个月
            if (now.getDayOfMonth() < petBirthday.getDayOfMonth()) {
                petAgeInMonths--;
            }
            // 确保月龄不为负数
            petAgeInMonths = Math.max(0, petAgeInMonths);

            // 匹配生命周期阶段
            String breedId = petBasicInfo.getBreedId();
            PetSpecies petSpecies = petSpeciesService.getSpeciesByBreed(breedId);
            List<PetLifeStage> lifeStages = petLifeStageService.list(
                    new LambdaQueryWrapper<PetLifeStage>()
                            .eq(PetLifeStage::getSpeciesId, petSpecies.getSpeciesId())
                            .orderByAsc(PetLifeStage::getSort)
            );
            for (PetLifeStage lifeStage : lifeStages) {
                int minAgeMonth = lifeStage.getMinAgeMonth();
                int maxAgeMonth = lifeStage.getMaxAgeMonth();
                if(petAgeInMonths >= minAgeMonth && ( petAgeInMonths < maxAgeMonth || maxAgeMonth == -1)){
                    petBasicInfo.setLifeStage(lifeStage.getStageId());
                    break;
                }
            }
        }else {
            petBasicInfo.setLifeStage("");
        }
        return petBasicInfo;
    }

    @Override
    public PageResult<PetBasicInfo> getMyPetPage(PageQuery page, PetBasicInfo petBasicInfo) {
        Map<String, Object> params = BeanUtil.beanToMap(page);
        // 当前用户
        petBasicInfo.setUserId(jwtUtil.getCurrentUserId());
        params.putAll(BeanUtil.beanToMap(petBasicInfo));
        return selectPageList( params );
    }

    @Override
    public List<PetBasicInfo> getMyPetList(PetBasicInfo petBasicInfo) {
        // 当前用户
        petBasicInfo.setUserId(jwtUtil.getCurrentUserId());
        Map<String, Object> params = new HashMap<>(BeanUtil.beanToMap(petBasicInfo));
        params.put("joinLifeStage", "joinLifeStage");
        params.put("joinSpecies", "joinSpecies");
        return selectAllList( params );
    }

    @Override
    public PetBasicInfoVo getBasicInfo() {
        // 获取当前用户所有宠物
        List<PetBasicInfo> petBasicInfoList = getMyPetList(new PetBasicInfo());
        petBasicInfoList.forEach(pet -> {
            // 计算月龄与年龄
            calcAge( pet );

            //获取最新体重记录
            PetWeightRecord weightRecord = petWeightRecordService.list(
                    new LambdaQueryWrapper<PetWeightRecord>()
                            .eq(PetWeightRecord::getPetId, pet.getPetId())
                            .orderByDesc(PetWeightRecord::getCreateTime)
            ).stream().findFirst().orElse(null);
            pet.setPetWeight(weightRecord != null ? weightRecord.getWeight() : null);
        });

        PetBasicInfoVo petBasicInfoVo = new PetBasicInfoVo();
        petBasicInfoVo.setPetBasicInfoList(petBasicInfoList);

        // TODO 健康概览 今日日程
        return petBasicInfoVo;
    }

    private void calcAge(PetBasicInfo petBasicInfo) {
        LocalDate petBirthday = petBasicInfo.getPetBirthday();
        if (petBirthday == null) {
            petBasicInfo.setMonthAge(BigDecimal.ZERO);
            petBasicInfo.setYearAge(BigDecimal.ZERO);
            return;
        }

        LocalDate now = LocalDate.now();

        // 计算总天数
        int totalDays = Math.toIntExact(ChronoUnit.DAYS.between(petBirthday, now));

        if (totalDays < 0) {
            petBasicInfo.setMonthAge(BigDecimal.ZERO);
            petBasicInfo.setYearAge(BigDecimal.ZERO);
            return;
        }

        // 计算月龄
        BigDecimal monthAge = BigDecimal.valueOf(totalDays)
                .divide(BigDecimal.valueOf(30.44), 0, RoundingMode.FLOOR);

        // 计算年龄
        BigDecimal yearAge = BigDecimal.valueOf(totalDays)
                .divide(BigDecimal.valueOf(365.25), 0, RoundingMode.FLOOR);

        petBasicInfo.setMonthAge(monthAge);
        petBasicInfo.setYearAge(yearAge);
    }

    @Override
    public void updatePet(PetBasicInfo petBasicInfo) {
        String userId = jwtUtil.getCurrentUserId();
        petBasicInfo.setUpdateUserId(userId);

        // 计算生命周期
        petBasicInfo = calcLifeStage(petBasicInfo);

        updateWithBean(petBasicInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePetByUserId(String userId){
        List<PetBasicInfo> list = list(new LambdaQueryWrapper<PetBasicInfo>().eq(PetBasicInfo::getUserId, userId));
        list.forEach(pet -> {
            pet = calcLifeStage(pet);
        });
        saveOrUpdateBatch(list);
    }
}
