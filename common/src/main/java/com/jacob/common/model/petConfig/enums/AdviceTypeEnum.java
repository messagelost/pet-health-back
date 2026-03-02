package com.jacob.common.model.petConfig.enums;

import lombok.Getter;

@Getter
public enum AdviceTypeEnum {

    NUTRITION_ADVICE(0, "营养建议"),
    EXERCISE_ADVICE(1, "运动建议"),
    DEWORMING_ADVICE(2, "驱虫建议"),
    VACCINE_ADVICE(3, "疫苗建议"),
    GROOMING_ADVICE(4, "洗护建议"),
    HEALTH_MONITORING(5, "健康监测");

    private final Integer value;
    private final String label;

    AdviceTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    public static AdviceTypeEnum fromValue(Integer value) {
        for (AdviceTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid advice type value: " + value);
    }

}
