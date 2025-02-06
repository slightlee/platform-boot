package com.demain.authorization.server.convert;

import com.demain.authorization.server.customize.domain.Oauth2UnionUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * 第三方账号转换类
 *
 * @author demain_lee
 * @since 0.0.1
 */
public interface OAuth2UserConvert {
    
    /**
     * 转换成自定义用户
     *
     * @param oAuth2User Oauth2用户
     * @return Oauth2UnionUser
     */
    Oauth2UnionUser convert(OAuth2User oAuth2User);
}