package com.jacob.common.model.petData.enums;

import lombok.Getter;

@Getter
public enum EventStatusEnum {
    NOT_REMIND(0, "未提醒"),
    REMINDED(1, "已提醒"),
    CANCELLED(2, "已取消");

    private final Integer code;
    private final String name;

    EventStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据 code 获取枚举
     */
    public static EventStatusEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (EventStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
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
