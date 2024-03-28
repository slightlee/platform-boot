package com.demain.framework.swagger.config;

import com.demain.framework.swagger.props.SwaggerProperties;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * swagger 配置
 *
 * @author demain_lee
 * @since 2024/1/3
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(name = {"springdoc.api-docs.enabled"}, havingValue = "true")
public class SwaggerAutoConfiguration {
    
    private final SwaggerProperties properties;
    
    public SwaggerAutoConfiguration(SwaggerProperties properties) {
        this.properties = properties;
    }
    
    @Bean
    public OpenAPI customOpenApi() {
        
        return new OpenAPI()
                .info(new Info()
                        .title(properties.getTitle())
                        .description(properties.getDescription())
                        .contact(
                                new Contact()
                                        .name(properties.getName())
                                        .url(properties.getUrl())
                                        .email(properties.getEmail()))
                        .termsOfService(properties.getTermsOfService())
                        .license(
                                new License()
                                        .name(properties.getLicenseName())
                                        .url(properties.getLicenseUrl()))
                        .version(properties.getVersion()))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringDoc Full Documentation")
                        .url("https://springdoc.org/"));
        
    }
    
}
