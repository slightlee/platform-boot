package com.demain.platform.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 *  实体类 日期 自动填充  ..
 *   @TableField(fill = FieldFill.INSERT)
 *   @TableField(fill = FieldFill.INSERT_UPDATE)
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        // 属性名称，不是字段名称    // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, "createTime", LocalDateTime::now, LocalDateTime.class);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime::now, LocalDateTime.class);
    }
}
