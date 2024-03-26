package com.demain.framework.web.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页响应封装类
 *
 * @author demain_lee
 * @since 2024/01/29
 */
@Data
@Schema(description = "分页响应对象")
public class PageResponse<T> implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1554353285437291215L;
    
    /**
     * 当前页
     */
    @Schema(description = "当前页")
    private Long current;
    
    /**
     * 每页显示多少条
     */
    @Schema(description = "每页显示多少条")
    private Long size;
    
    /**
     * 总条数
     */
    @Schema(description = "总条数")
    private Long total;
    
    /**
     * 数据
     */
    @Schema(description = "数据")
    private List<T> records = Collections.emptyList();
    
    private PageResponse() {
    }
    
    public static <T> PageResponseBuilder<T> builder() {
        return new PageResponseBuilder<>();
    }
    
    public static class PageResponseBuilder<T> {
        
        private Long current;
        private Long size;
        private List<T> records;
        private Long total;
        
        public PageResponseBuilder<T> current(Long current) {
            this.current = current;
            return this;
        }
        
        public PageResponseBuilder<T> size(Long size) {
            this.size = size;
            return this;
        }
        
        public PageResponseBuilder<T> records(List<T> records) {
            this.records = records;
            return this;
        }
        
        public PageResponseBuilder<T> total(Long total) {
            this.total = total;
            return this;
        }
        
        public PageResponse<T> build() {
            PageResponse<T> pageResponse = new PageResponse<>();
            pageResponse.current = this.current;
            pageResponse.size = this.size;
            pageResponse.total = this.total;
            pageResponse.records = this.records;
            return pageResponse;
        }
    }
    
}