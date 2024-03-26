package com.demain.framework.mybatis.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.demain.framework.mybatis.props.SlowSQLProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;

/**
 * 慢SQL 拦截器
 *
 * @author demain_lee
 * @since 2024/1/29
 */
@Slf4j
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
@RequiredArgsConstructor
public class SlowSqlInterceptor implements Interceptor {
    
    private final SlowSQLProperties slowSQLProperties;
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        
        Object target = PluginUtils.realTarget(invocation.getTarget());
        long startTime = SystemClock.now();
        
        StatementHandler statementHandler = (StatementHandler) target;
        try {
            return invocation.proceed();
        } finally {
            long costTime = SystemClock.now() - startTime;
            BoundSql boundSql = statementHandler.getBoundSql();
            String sql = boundSql.getSql();
            sql = sql.replaceAll("\\n+", " ");
            MetaObject metaObject = SystemMetaObject.forObject(target);
            MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
            log.info("执行ID:{}，执行SQL: [{}] ，执行耗时 {} ms ", ms.getId(), sql, costTime);
            if (costTime >= slowSQLProperties.getCost()) {
                log.warn("警告！！！，监测到慢查询SQL: 执行ID:{}，执行SQL: [{}] ，执行耗时 {} ms ", ms.getId(), sql, costTime);
            }
        }
    }
    
}
