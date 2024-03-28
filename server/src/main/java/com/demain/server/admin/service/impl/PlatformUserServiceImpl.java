package com.demain.server.admin.service.impl;

import com.demain.server.admin.entity.PlatformUser;
import com.demain.server.admin.mapper.PlatformUserMapper;
import com.demain.server.admin.service.PlatformUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author demain_lee
 * @since 2021-12-14
 */
@Service
public class PlatformUserServiceImpl extends ServiceImpl<PlatformUserMapper, PlatformUser>
        implements PlatformUserService {
    
}
