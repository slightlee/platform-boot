package com.demain.authorization.server.customize.service;

import com.demain.authorization.server.convert.Oauth2UserConverterContext;
import com.demain.authorization.server.customize.domain.Oauth2UnionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 联合登录服务
 *
 * @author demain_lee
 * @since 0.0.1
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOauth2UnionService extends DefaultOAuth2UserService {
    
    private final Oauth2UserConverterContext oauth2UserConverterContext;
    
    private final Oauth2ThirdService oauth2ThirdService;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        
        log.info("-------------------- oauth2 user ------------------------");
        
        // 1、获取第三方用户信息
        OAuth2User oAuth2User = super.loadUser(userRequest);
        // 2、转换用户信息
        Oauth2UnionUser oauth2UnionUser = oauth2UserConverterContext.convert(userRequest, oAuth2User);
        // 3、检查是否存在并保存
        oauth2ThirdService.save(oauth2UnionUser);
        
        // 将yml配置的RegistrationId当做登录类型添加至attributes中
        LinkedHashMap<String, Object> attributes = new LinkedHashMap<>(oAuth2User.getAttributes());
        attributes.put("platform", oauth2UnionUser.getPlatform());
        
        return new DefaultOAuth2User(oAuth2User.getAuthorities(), attributes,
                oauth2UnionUser.getUserNameAttributeName());
    }
    
}
