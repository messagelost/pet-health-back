package com.jacob.common.model.petConfig.enums;

import lombok.Getter;

@Getter
public enum PeriodUnitEnum {

    DAY(1, "天"),
    MONTH(2, "月"),
    YEAR(3, "年");

    private final Integer value;
    private final String label;

    PeriodUnitEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

}
