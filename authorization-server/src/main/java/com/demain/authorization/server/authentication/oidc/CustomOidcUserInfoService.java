package com.demain.authorization.server.authentication.oidc;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.demain.authorization.server.customize.entity.PlatformUser;
import com.demain.authorization.server.customize.mapper.PlatformUserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOidcUserInfoService {
    
    @Resource
    private PlatformUserMapper platformUserMapper;
    
    public CustomOidcUserInfo loadUserInfo(String username) {
        
        PlatformUser platformUser = platformUserMapper.selectOne(Wrappers.<PlatformUser>lambdaQuery()
                .eq(PlatformUser::getAccount, username).eq(PlatformUser::getIsDelete, 0));
        
        return new CustomOidcUserInfo(this.createUserInfo(platformUser));
    }
    
    private Map<String, Object> createUserInfo(PlatformUser platformUser) {
        return CustomOidcUserInfo.cusBuilder()
                .userName(platformUser.getAccount())
                .name(platformUser.getRealName())
                .email(platformUser.getEmail())
                .phoneNumber(platformUser.getPhone())
                .status(platformUser.getStatus())
                .profile("http://www.xxxx.com/")
                .build().getClaims();
    }
}
