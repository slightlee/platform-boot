package com.demain.platform.auth.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
  * Oauth2Token
  * @author 明天
  * @since 2022/3/25 14:52
  */
@Data
@Builder
public class Oauth2Token implements Serializable {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 访问令牌头前缀
     */
    private String tokenHeadPrefix;
    /**
     * 有效时间（秒）
     */
    private Integer expiresIn;
}
