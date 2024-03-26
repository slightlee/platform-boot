package com.demain.framework.mybatis.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.demain.framework.mybatis.handlers.MyMetaObjectHandler;
import com.demain.framework.mybatis.injector.MyLogicSqlInjector;
import com.demain.framework.mybatis.interceptor.SlowSqlInterceptor;
import com.demain.framework.mybatis.props.SlowSQLProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 *
 * @author demain_lee
 * @since 2024/1/2
 */
@Configuration
@EnableConfigurationProperties(SlowSQLProperties.class)
public class MybatisPlusConfiguration {
    
    private final SlowSQLProperties slowSqlProperties;
    
    public MybatisPlusConfiguration(SlowSQLProperties slowSqlProperties) {
        this.slowSqlProperties = slowSqlProperties;
    }
    
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 分页插件 DbType：数据库类型(根据类型获取应使用的分页方言)
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        // interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        
        return interceptor;
    }
    
    /**
     * 自定义sql注入器
     */
    @Bean
    public MyLogicSqlInjector myLogicSqlInjector() {
        return new MyLogicSqlInjector();
    }
    
    /**
     * 插入、更新 数据时自动填充默认值
     */
    @Bean
    public MyMetaObjectHandler myMetaObjectHandler() {
        return new MyMetaObjectHandler();
    }
    
    /**
     * 注入mybatis插件 统计SQL执行耗时
     */
    @Bean
    @ConditionalOnProperty(name = "platform.sql.slow.enable", havingValue = "true", matchIfMissing = true)
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> configuration.addInterceptor(new SlowSqlInterceptor(slowSqlProperties));
    }
}
