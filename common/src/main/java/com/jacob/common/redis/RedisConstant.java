package com.jacob.common.redis;

import lombok.Getter;

@Getter
public enum RedisConstant {

    USER_ROLES("PetHealth:UserRoles:", "用户角色"),
    USER_MENUS("PetHealth:UserMenus:", "用户菜单"),

    NOTIFY_SCHEDULE("PetHealth:Notify:Schedule:", "通知队列"),
    NOTIFY_PROCESSING("PetHealth:Notify:Processing:", "通知处理中"),
    NOTIFY_RETRY("PetHealth:Notify:Retry:", "失败重试"),

    NOTIFY_TEMPLATE("PetHealth:Notify:Template:", "通知模板"),
    NOTIFY_SEND("PetHealth:Notify:Send:", "通知已发送");

    private final String code;
    private final String desc;

    RedisConstant(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
