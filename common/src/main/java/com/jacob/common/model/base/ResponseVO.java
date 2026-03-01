package com.jacob.common.model.base;


import com.jacob.common.exception.HttpStatusEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseVO<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    public static <T> ResponseVO<T> success() {
        ResponseVO<T> r = new ResponseVO<T>();
        r.setCode(HttpStatusEnum.SUCCESS.getCode());
        r.setMessage(HttpStatusEnum.SUCCESS.getMsg());
        return r;
    }

    public static <T> ResponseVO<T> success( T data ) {
        ResponseVO<T> r = new ResponseVO<T>();
        r.setCode(HttpStatusEnum.SUCCESS.getCode());
        r.setMessage(HttpStatusEnum.SUCCESS.getMsg());
        r.setData(data);
        return r;
    }

    public static <T> ResponseVO<T> success( T data, String msg ) {
        ResponseVO<T> r = new ResponseVO<T>();
        r.setCode(HttpStatusEnum.SUCCESS.getCode());
        r.setMessage(msg);
        r.setData(data);
        return r;
    }

    public static <T> ResponseVO<T> error() {
        ResponseVO<T> r = new ResponseVO<T>();
        r.setCode(HttpStatusEnum.SERVER_ERROR.getCode());
        r.setMessage(HttpStatusEnum.SERVER_ERROR.getMsg());
        return r;
    }

    public static <T> ResponseVO<T> error( T data ) {
        ResponseVO<T> r = new ResponseVO<T>();
        r.setCode(HttpStatusEnum.SERVER_ERROR.getCode());
        r.setMessage(HttpStatusEnum.SERVER_ERROR.getMsg());
        r.setData(data);
        return r;
    }

    public static <T> ResponseVO<T> error( T data, String msg ) {
        ResponseVO<T> r = new ResponseVO<T>();
        r.setCode(HttpStatusEnum.SERVER_ERROR.getCode());
        r.setMessage(msg);
        r.setData(data);
        return r;
    }

}
