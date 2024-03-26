package com.demain.framework.core.response;

/**
 * 状态码枚举
 * 错误码分为ABC三类:
 * A类对应客户端错误;
 * B类对应系统内部错误;
 * C类对应第三方接口调用错误;
 *
 * @author demain_lee
 * @since 0.0.1
 */
public enum ResponseCode {
    
    // 000000 - 成功
    OK("00000", "操作成功"),
    
    // A01XX 校验相关
    CLIENT_ERROR("A0001", "客户端错误"),
    USER_NOT_FOUND("A0110", "用户不存在"),
    USER_ALREADY_EXISTS("A0111", "用户已存在"),
    USERNAME_CONTAINS_SENSITIVE_WORDS("A0112", "用户名包含敏感词"),
    USERNAME_CONTAINS_SPECIAL_CHARACTERS("A0113", "用户名包含特殊字符"),
    PASSWORD_CHECK_FAILED("A0120", "密码校验失败"),
    PASSWORD_TOO_SHORT("A0121", "密码长度不够"),
    PASSWORD_WEAK_STRENGTH("A0122", "密码强度不够"),
    VERIFICATION_CODE_INCORRECT("A0130", "校验码输入错误"),
    SMS_VERIFICATION_CODE_INCORRECT("A0131", "短信校验码输入错误"),
    EMAIL_VERIFICATION_CODE_INCORRECT("A0132", "邮件校验码输入错误"),
    INVALID_PHONE_FORMAT("A0151", "手机格式校验失败"),
    INVALID_ADDRESS_FORMAT("A0152", "地址格式校验失败"),
    INVALID_EMAIL_FORMAT("A0153", "邮箱格式校验失败"),
    
    // A02XX 登录相关
    USER_ACCOUNT_NOT_FOUND("A0201", "用户账户不存在"),
    USER_ACCOUNT_IS_FROZEN("A0202", "用户账户被冻结"),
    USER_ACCOUNT_IS_INVALIDATED("A0203", "用户账户已作废"),
    USER_PASSWORD_INCORRECT("A0210", "用户密码错误"),
    USER_PASSWORD_ATTEMPTS_EXCEEDED("A0211", "用户输入密码错误次数超限"),
    USER_IDENTITY_VERIFICATION_FAILED("A0220", "用户身份校验失败"),
    USER_FINGERPRINT_VERIFICATION_FAILED("A0221", "用户指纹识别失败"),
    USER_FACE_RECOGNITION_FAILED("A0222", "用户面容识别失败"),
    USER_NO_THIRD_PARTY_AUTHORIZATION("A0223", "用户未获得第三方登录授权"),
    USER_LOGIN_EXPIRED("A0230", "用户登录已过期"),
    USER_VERIFICATION_CODE_INCORRECT("A0240", "用户验证码错误"),
    USER_VERIFICATION_CODE_ATTEMPTS_EXCEEDED("A0241", "用户验证码尝试次数超限"),
    
    // A03XX 访问权限相关
    UNAUTHORIZED_ACCESS("A0301", "访问未授权"),
    AUTHORIZING("A0302", "正在授权中"),
    USER_AUTHORIZATION_DENIED("A0303", "用户授权申请被拒绝"),
    PRIVACY_SETTINGS_INTERCEPTED("A0310", "因访问对象隐私设置被拦截"),
    AUTHORIZATION_EXPIRED("A0311", "授权已过期"),
    NO_API_PERMISSION("A0312", "无权限使用 API"),
    USER_ACCESS_INTERCEPTED("A0320", "用户访问被拦截"),
    BLACKLISTED_USER("A0321", "黑名单用户"),
    ACCOUNT_FROZEN("A0322", "账号被冻结"),
    ILLEGAL_IP_ADDRESS("A0323", "非法 IP 地址"),
    GATEWAY_ACCESS_RESTRICTED("A0324", "网关访问受限"),
    
    // AO7xx 上传文件相关
    INVALID_FILE_TYPE("A0701", "用户上传文件类型不匹配"),
    FILE_TOO_LARGE("A0702", "用户上传文件太大"),
    IMAGE_TOO_LARGE("A0703", "用户上传图片太大"),
    VIDEO_TOO_LARGE("A0704", "用户上传视频太大"),
    COMPRESSED_FILE_TOO_LARGE("A0705", "用户上传压缩文件太大"),
    
    // B 系统内部
    SERVICE_ERROR("B0001", "系统内部错误"),
    SYSTEM_TIMEOUT("B0100", "系统执行超时"),
    SYSTEM_LIMIT_FLOW("B0210", "系统限流"),
    SYSTEM_FUNCTION_DEGRADATION("B0220", "系统功能降级"),
    SYSTEM_RESOURCE_EXCEPTION("B0300", "系统资源异常"),
    SYSTEM_RESOURCE_EXHAUSTED("B0310", "系统资源耗尽"),
    DISK_SPACE_EXHAUSTED("B0311", "系统磁盘空间耗尽"),
    MEMORY_EXHAUSTED("B0312", "系统内存耗尽"),
    FILE_HANDLE_EXHAUSTED("B0313", "文件句柄耗尽"),
    CONNECTION_POOL_EXHAUSTED("B0314", "系统连接池耗尽"),
    THREAD_POOL_EXHAUSTED("B0315", "系统线程池耗尽"),
    SYSTEM_RESOURCE_ACCESS_EXCEPTION("B0320", "系统资源访问异常"),
    FAILED_TO_READ_DISK_FILE("B0321", "系统读取磁盘文件失败"),
    
    // C 第三方
    THIRD_PARTY_SERVICE_ERROR("C0001", "调用第三方服务出错");
    
    /**
     * 错误类型码
     */
    private final String code;
    
    /**
     * 错误信息
     */
    private final String message;
    
    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
}
