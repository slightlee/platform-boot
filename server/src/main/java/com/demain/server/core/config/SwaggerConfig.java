package com.demain.server.core.config;


import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * swagger 配置
 *
 * @author demain_lee
 * @since 2024/1/3
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("系统服务1")
                .pathsToMatch("/admin/**")
                .build();
    }

    @Bean
    public GroupedOpenApi demoApi() {
        return GroupedOpenApi.builder()
                .group("demo服务1")
                .pathsToMatch("/demo/**")
                .build();
    }

}
