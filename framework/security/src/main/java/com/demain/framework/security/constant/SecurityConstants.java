package com.demain.framework.security.constant;

/**
 * 常量类
 *
 * @author demain_lee
 * @since 0.0.1
 */
public class SecurityConstants {
    
    /**
     * 登录方式——账号密码登录
     */
    public static final String PASSWORD_LOGIN_TYPE = "passwordLogin";
    
    /**
     * 密码模式（自定义）
     */
    public static final String GRANT_TYPE_PASSWORD = "password";
    
    /**
     * 短信验证码模式（自定义）
     */
    public static final String GRANT_TYPE_SMS = "sms_verification_code";
    
    /**
     * 短信验证码
     */
    public static final String SMS_VERIFICATION_CODE = "verificationCode";
    
    /**
     * 短信验证码默认值
     */
    public static final String SMS_CODE_VALUE = "6666";
    
    /**
     * 登录地址
     */
    public static final String LOGIN_URL = "/login";
}
