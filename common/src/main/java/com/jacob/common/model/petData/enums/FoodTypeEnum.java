package com.jacob.common.model.petData.enums;

import lombok.Getter;

@Getter
public enum FoodTypeEnum {

    DRY_FOOD(1, "干粮"),
    WET_FOOD(2, "湿粮"),
    HOMEMADE_FOOD(3, "自制粮"),
    SNACK(4, "零食"),
    SUPPLEMENT(5, "营养补充剂"),
    OTHER(6, "其他");

    private final int code;
    private final String description;

    FoodTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static FoodTypeEnum valueOfCode(int code) {
        for (FoodTypeEnum type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }
}
