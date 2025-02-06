package com.demain.authorization.server.customize.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.demain.authorization.server.customize.domain.Oauth2UnionUser;
import com.demain.authorization.server.customize.entity.PlatformUser;
import com.demain.authorization.server.customize.mapper.PlatformUserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demain.authorization.server.customize.service.PlatformUserService;
import org.springframework.stereotype.Service;

@Service
public class PlatformUserServiceImpl extends ServiceImpl<PlatformUserMapper, PlatformUser> implements
        PlatformUserService {
    
    private final PlatformUserMapper platformUserMapper;
    
    public PlatformUserServiceImpl(PlatformUserMapper platformUserMapper) {
        this.platformUserMapper = platformUserMapper;
    }
    
    @Override
    public PlatformUser loadUserInfo(String username) {
        return platformUserMapper.selectOne(Wrappers.<PlatformUser>lambdaQuery()
                .eq(PlatformUser::getAccount, username).eq(PlatformUser::getIsDelete, 0));
    }
    
    @Override
    public Long saveByThirdUser(Oauth2UnionUser oauth2UnionUser) {
        PlatformUser platformUser = convertOauth2UnionUser(oauth2UnionUser);
        platformUserMapper.insert(platformUser);
        return platformUser.getId();
    }
    
    /**
     * 转换第三方用户信息
     * 
     * @param oauth2UnionUser 第三方用户信息
     * @return 系统用户信息
     */
    private PlatformUser convertOauth2UnionUser(Oauth2UnionUser oauth2UnionUser) {
        PlatformUser platformUser = new PlatformUser();
        platformUser.setNickname(oauth2UnionUser.getNickName());
        platformUser.setAccount(oauth2UnionUser.getUniqueAccount());
        platformUser.setEmail(oauth2UnionUser.getEmail());
        platformUser.setAvatar(oauth2UnionUser.getAvatarUrl());
        platformUser.setPlatformSource(oauth2UnionUser.getPlatform());
        return platformUser;
    }
}
