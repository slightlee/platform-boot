package com.demain.framework.swagger.props;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * swagger 配置
 *
 * @author demain_lee
 * @since 2024/1/3
 */
@Component
@ConfigurationProperties(prefix = "platform.swagger")
@Data
public class SwaggerProperties {

    private String title;

    private String version;

    private String termsOfService;

    private String description;

    private String name;

    private String url;

    private String email;

    private String licenseName;

    private String licenseUrl;

}
