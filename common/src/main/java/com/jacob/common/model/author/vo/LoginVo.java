package com.jacob.common.model.author.vo;

import lombok.Data;

@Data
public class LoginVo {
    private String userId;
    private String username;
    private String jwt;
    private String nickName;
}
