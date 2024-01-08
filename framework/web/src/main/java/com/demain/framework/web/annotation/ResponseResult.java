package com.demain.framework.web.annotation;

import java.lang.annotation.*;

/**
 * 自定义响应结果注解
 *
 * @author demain_lee
 * @since 2023/5/11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ResponseResult {
}
