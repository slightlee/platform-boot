package com.demain.framework.security.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
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
}