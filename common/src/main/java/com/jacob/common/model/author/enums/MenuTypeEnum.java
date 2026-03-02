package com.jacob.common.model.author.enums;

import lombok.Getter;

@Getter
public enum MenuTypeEnum {

    CATALOG(1, "目录"),
    MENU(2, "菜单"),
    BUTTON(3, "按钮");

    private final Integer code;
    private final String desc;

    MenuTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code获取枚举
     */
    public static MenuTypeEnum getByCode(Integer code) {
        for (MenuTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 判断code是否存在
     */
    public static boolean exists(Integer code) {
        return getByCode(code) != null;
    }
}

