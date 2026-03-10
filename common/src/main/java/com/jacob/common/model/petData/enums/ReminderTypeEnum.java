package com.jacob.common.model.petData.enums;

import lombok.Getter;

@Getter
public enum ReminderTypeEnum {
    ONCE("once", "单次提醒"),
    REPEAT("repeat", "周期提醒"),;

    private final String code;
    private final String name;

    ReminderTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据 code 获取枚举
     */
    public static ReminderTypeEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (ReminderTypeEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 判断 code 是否有效
     */
    public static boolean isValid(String code) {
        return getByCode(code) != null;
    }
}
