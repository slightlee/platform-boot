package com.demain.platform.core.config;


import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
@ConfigurationProperties(prefix="platform.swagger")
@Data
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SwaggerConfig {

    private String title;

    private String version;

    private String termsOfServiceUrl;

    private String description;

    private String basePackage;

    private String name;

    private String url;

    private String email;

    private String groupName;

    private final OpenApiExtensionResolver openApiExtensionResolver;

    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)

                // 添加 自定义 footer
                .extensions(openApiExtensionResolver.buildSettingExtensions())
                // 添加 自定义文档
                .groupName(groupName)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.demain.platform"))
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(name,url,email))
                .version(version)
                .build();
    }


}
