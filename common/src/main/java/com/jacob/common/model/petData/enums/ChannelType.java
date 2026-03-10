package com.jacob.common.model.petData.enums;

import lombok.Getter;

@Getter
public enum ChannelType {
    SMS("sms", "短信提醒"),
    EMAIL("email", "邮箱提醒"),
    SITE("site", "站内提醒");

    private final String code;
    private final String name;

    ChannelType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据 code 获取枚举
     */
    public static ChannelType getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (ChannelType status : values()) {
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
