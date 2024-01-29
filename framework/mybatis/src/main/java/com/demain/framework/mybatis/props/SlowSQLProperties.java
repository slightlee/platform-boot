package com.demain.framework.mybatis.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 慢SQL配置
 *
 * @author demain_lee
 * @since 2024/1/29
 */
@ConfigurationProperties(prefix = "platform.sql.slow")
@Data
public class SlowSQLProperties {

    /**
     * 是否开启慢查询
     */
    private boolean enable = false;

    /**
     * 默认2s
     */
    private long cost = 2000L;

}
