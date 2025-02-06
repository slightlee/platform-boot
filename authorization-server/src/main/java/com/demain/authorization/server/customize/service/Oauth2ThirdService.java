package com.demain.authorization.server.customize.service;

import com.demain.authorization.server.customize.domain.Oauth2UnionUser;

/**
 * 第三方用户服务
 *
 * @author demain_lee
 * @since 0.0.1
 */
public interface Oauth2ThirdService {
    
    /**
     * 保存三方用户
     */
    void save(Oauth2UnionUser oauth2UnionUser);
    
}
