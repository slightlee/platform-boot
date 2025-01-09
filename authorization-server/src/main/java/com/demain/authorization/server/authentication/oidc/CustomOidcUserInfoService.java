package com.demain.authorization.server.authentication.oidc;

import com.demain.authorization.server.customize.entity.PlatformUser;
import com.demain.authorization.server.customize.service.PlatformUserService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 自定义用户信息服务
 *
 * @author demain_lee
 * @since 2025/01/09
 */
@Service
public class CustomOidcUserInfoService {
    
    private final PlatformUserService platformUserService;
    
    public CustomOidcUserInfoService(PlatformUserService platformUserService) {
        this.platformUserService = platformUserService;
    }
    
    public CustomOidcUserInfo loadUserInfo(String username) {
        PlatformUser platformUser = platformUserService.loadUserInfo(username);
        return new CustomOidcUserInfo(this.createUserInfo(platformUser));
    }
    
    private Map<String, Object> createUserInfo(PlatformUser platformUser) {
        return CustomOidcUserInfo.cusBuilder()
                .userName(platformUser.getAccount())
                .name(platformUser.getRealName())
                .nickname(platformUser.getNickname())
                .email(platformUser.getEmail())
                .phoneNumber(platformUser.getPhone())
                .avatar(platformUser.getAvatar())
                .status(platformUser.getStatus())
//                .profile("https://www.xxxx.com/")
                .build().getClaims();
    }
}
