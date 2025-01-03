package com.demain.authorization.server.customize.entity;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseEntity implements Serializable {
    
    @Serial
    private static final long serialVersionUID = -4942375242032056890L;
    
    private Long id;
    
    private Long tenantId;
    
    private Long creator;
    
    private LocalDateTime createTime;
    
    private Long operator;
    
    private LocalDateTime updateTime;
    
    /**
     * 是否删除 0:启用,1:删除
     */
    private Integer isDelete;
    
}
