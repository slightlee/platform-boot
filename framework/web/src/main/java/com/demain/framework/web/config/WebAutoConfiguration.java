package com.demain.framework.web.config;

import com.demain.framework.web.handler.GlobalExceptionHandler;
import com.demain.framework.web.handler.ResponseResultHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自动化配置
 *
 * @author demain_lee
 * @since 2024/1/22
 */
@Configuration
@ConditionalOnWebApplication
public class WebAutoConfiguration {
    
    /**
     * 自定义全局异常处理器
     */
    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
    
    /**
     * 接口包装
     */
    @Bean
    @ConditionalOnMissingBean(ResponseResultHandler.class)
    public ResponseResultHandler responseResultHandler() {
        return new ResponseResultHandler();
    }
    
}
