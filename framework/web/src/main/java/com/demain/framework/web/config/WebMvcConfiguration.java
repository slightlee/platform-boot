package com.demain.framework.web.config;

import com.demain.framework.web.interceptor.ResponseResultInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web 配置
 *
 * @author demain_lee
 * @since 2024/1/8
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    
    /**
     * 添加自定义拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResponseResultInterceptor()).addPathPatterns("/**");
    }
    //
    // /**
    // * 跨域配置
    // */
    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    // registry
    // .addMapping("/**")
    // // 允许跨域的域名
    // //.allowedOrigins("http://yyy.com", "http://xxx.com")
    // // 允许所有域
    // .allowedOriginPatterns("*")
    // // 允许任何方法（post、get等）
    // .allowedMethods("*")
    // // 允许任何请求头
    // .allowedHeaders("*")
    // // 允许证书、cookie
    // .allowCredentials(true)
    // .exposedHeaders(HttpHeaders.SET_COOKIE)
    // // maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
    // .maxAge(3600L);
    // }
    
}
