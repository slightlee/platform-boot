package com.demain.framework.swagger.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * swagger 配置
 *
 * @author demain_lee
 * @since 2024/1/3
 */
@Configuration
@ConfigurationProperties(prefix = "platform.swagger")
@Data
public class SwaggerConfig {

    private String title;

    private String version;

    private String termsOfService;

    private String description;

    private String name;

    private String url;

    private String email;

    private String licenseName;

    private String licenseUrl;


    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .description(description)
                        .contact(
                                new Contact().name(name).url(url).email(email))
                        .termsOfService(termsOfService)
                        .license(new License().name(licenseName).url(licenseUrl))
                        .version(version)
                )
                .externalDocs(new ExternalDocumentation()
                        .description("SpringDoc Full Documentation")
                        .url("https://springdoc.org/"));
    }


}
