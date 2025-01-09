package com.demain.authorization.server.customize.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demain.authorization.server.customize.entity.PlatformUser;

public interface PlatformUserService extends IService<PlatformUser> {
    
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    PlatformUser loadUserInfo(String username);
    
}
