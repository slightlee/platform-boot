package com.demain.framework.security.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 资源服务器配置
 *
 * @author demain_lee
 * @since 0.0.1
 */
@ConfigurationProperties(prefix = "platform.resource.server")
public class ResourceServerProperties {
    
    /**
     * 是否启用资源服务器配置
     */
    private boolean enabled = true;
    
    /**
     * 是否启用方法安全
     */
    private boolean enableMethodSecurity = true;
    
    /**
     * 白名单路径
     */
    private String[] whitelist = {};
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isEnableMethodSecurity() {
        return enableMethodSecurity;
    }
    
    public void setEnableMethodSecurity(boolean enableMethodSecurity) {
        this.enableMethodSecurity = enableMethodSecurity;
    }
    
    public String[] getWhitelist() {
        return whitelist;
    }
    
    public void setWhitelist(String[] whitelist) {
        this.whitelist = whitelist;
    }
}