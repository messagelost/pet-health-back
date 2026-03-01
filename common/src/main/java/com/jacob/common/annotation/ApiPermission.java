package com.jacob.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiPermission {

    /**
     * 权限编码
     * 例如：system:userRole:list
     */
    String code();

    /**
     * 权限名称
     */
    String name();

    /**
     * 是否自动注册到数据库
     */
    boolean register() default true;

}
