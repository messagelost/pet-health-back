package com.jacob.common.redis;

import lombok.Getter;

@Getter
public enum RedisConstant {

    USER_ROLES("PetHealth:UserRoles:", "用户角色"),
    USER_MENUS("PetHealth:UserMenus:", "用户菜单"),
    NOTIFY_SCHEDULE("PetHealth:Notify:Schedule:", "通知队列"),;

    private final String code;
    private final String desc;

    RedisConstant(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
