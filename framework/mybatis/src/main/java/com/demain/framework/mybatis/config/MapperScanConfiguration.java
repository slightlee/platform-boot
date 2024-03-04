package com.demain.framework.mybatis.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mapper scan 配置
 *
 * @author demain_lee
 * @since 2024/1/30
 */
@Configuration(proxyBeanMethods = false)
public class MapperScanConfiguration {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
        scannerConfigurer.setBasePackage("com.demain.server.*.mapper");
        return scannerConfigurer;
    }
}
