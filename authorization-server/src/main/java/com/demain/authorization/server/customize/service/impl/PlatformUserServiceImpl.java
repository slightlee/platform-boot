package com.demain.authorization.server.customize.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
}
