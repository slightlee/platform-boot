package com.demain.authorization.server.convert;

import com.demain.authorization.server.customize.domain.Oauth2UnionUser;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * 策略模式构造器，用户转换用户信息
 *
 * @author demain_lee
 * @since 0.0.1
 */
@Component
public class Oauth2UserConverterContext {
    
    /**
     * 用户转换器
     */
    public Oauth2UnionUser convert(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String userNameAttributeName =
                userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                        .getUserNameAttributeName();
        // 获取三方登录配置的registrationId，这里将他当做登录方式
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // 转换用户信息
        Oauth2UnionUser oauth2UnionUser = this.getInstance(registrationId).convert(oAuth2User);
        
        oauth2UnionUser.setUserNameAttributeName(userNameAttributeName);
        // 获取AccessToken
        OAuth2AccessToken accessToken = userRequest.getAccessToken();
        oauth2UnionUser.setCredentials(accessToken.getTokenValue());
        
        Instant expiresAt = accessToken.getExpiresAt();
        if (expiresAt != null) {
            LocalDateTime tokenExpiresAt = expiresAt.atZone(ZoneId.of("UTC")).toLocalDateTime();
            // token过期时间
            oauth2UnionUser.setCredentialsExpiresAt(tokenExpiresAt);
        }
        return oauth2UnionUser;
    }
    
    /**
     * 获取转换器
     *
     * @param registrationId 登录类型
     * @return 转换器
     */
    private OAuth2UserConvert getInstance(String registrationId) {
        if (Objects.isNull(registrationId)) {
            throw new UnsupportedOperationException("登录方式不能为空.");
        }
        
        return switch (registrationId) {
            case "github-idp" -> new GithubUserConvert();
            case "gitee" -> new GiteeUserConvert();
            default -> throw new IllegalStateException("Unexpected value: " + registrationId);
        };
        
    }
}
