package com.demain.framework.mybatis.handlers;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;


/**
 * 自动填充配置
 *
 * @author demain_lee
 * @since 2024/1/29
 */
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        // 属性名称，不是字段名称    // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);
//        this.strictInsertFill(metaObject, "creator", AuthUtil::getUserId, Long.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
//        this.strictUpdateFill(metaObject, "operator", AuthUtil::getUserId, Long.class);
    }
}
