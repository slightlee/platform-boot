package com.demain.platform.admin.service.impl;

import com.demain.platform.admin.entity.PlatformUser;
import com.demain.platform.admin.mapper.PlatformUserMapper;
import com.demain.platform.admin.service.PlatformUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 明天
 * @since 2021-12-14
 */
@Service
public class PlatformUserServiceImpl extends ServiceImpl<PlatformUserMapper, PlatformUser> implements PlatformUserService {

}
