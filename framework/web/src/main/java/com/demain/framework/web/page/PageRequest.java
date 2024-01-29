package com.demain.framework.web.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 分页请求
 *
 * @author demain_lee
 * @since 2024/1/29
 */
@Data
@Schema(description = "分页参数")
public class PageRequest {

    @Schema(description = "当前页数")
    private Long current;

    @Schema(description = "每页显示条数")
    private Long size;

    /**
     * 默认构造器，当分页参数不传时提供默认值
     */
    public PageRequest() {
        this.size = 10L;
        this.current = 1L;
    }
}
