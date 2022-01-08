package com.demain.platform.auth.enhancer;

import com.demain.platform.auth.entity.PlatformUserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
  *  jwt内容增强器
  * @Author 明天
  * @Date 2022/1/8 15:57
  */
@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> map = new HashMap<>(2);
        PlatformUserDetails user = (PlatformUserDetails) authentication.getUserAuthentication().getPrincipal();
        map.put("account", user.getAccount());
        map.put("realName", user.getRealName());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
        return accessToken;
    }
}
