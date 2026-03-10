package com.jacob.common.model.petData.enums;

import lombok.Getter;

@Getter
public enum EventTypeEnum {

    VACCINE(1, "疫苗"),
    DEWORMING(2, "驱虫"),
    GROOMING(3, "洗护"),
    MEDICATION(4, "用药"),
    EXAMINATION(5, "体检"),
    FEEDING(6, "喂食"),
    OTHER(7, "其他");

    private final Integer code;
    private final String name;

    EventTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据 code 获取枚举
     */
    public static EventTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (EventTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 判断 code 是否有效
     */
    public static boolean isValid(Integer code) {
        return getByCode(code) != null;
    }
}
