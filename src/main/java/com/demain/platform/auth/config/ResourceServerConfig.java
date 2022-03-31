package com.demain.platform.auth.config;

import com.demain.platform.core.contant.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * 资源服务配置
 *
 * @author 明天
 * @since 2022/1/11 15:05
 */
@Configuration
@EnableResourceServer
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final AccessDeniedHandler accessDeniedHandler;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                // 配置资源id 这里的资源id和授权服务器中的资源id一致
                .resourceId(Constants.RESOURCE_ID)
                .stateless(true)
                // Token失效方面异常
                .authenticationEntryPoint(authenticationEntryPoint)
                // 权限不足
                .accessDeniedHandler(accessDeniedHandler)


        ;
    }

    /**
     * 配置 URL 访问权限
     *
     * @param http
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/doc.html/**", "/swagger-resources", "/v2/api-docs/**", "/webjars/**").permitAll()
                .antMatchers("/admin/user/list2").hasAuthority("admin")
                .anyRequest().authenticated()
        ;
    }
}
