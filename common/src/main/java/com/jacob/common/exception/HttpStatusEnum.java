package com.jacob.common.exception;

import lombok.Getter;

@Getter
public enum HttpStatusEnum {

    SUCCESS(200, "成功"),

    PERMANENT_REDIRECT(301, "永久重定向"),

    TEMP_REDIRECT(302, "临时重定向"),

    REQUEST_ERROR(400, "请求错误"),

    UNAUTHORIZED(401, "无效或未登录"),

    FORBIDDEN(403, "无权限访问"),

    NOT_FOUND(404, "资源未找到"),

    SERVER_ERROR(500, "服务器内部错误");

    private final Integer code;

    private final String msg;


    HttpStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
