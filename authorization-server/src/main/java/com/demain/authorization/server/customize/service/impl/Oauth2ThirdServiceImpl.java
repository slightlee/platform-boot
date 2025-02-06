package com.demain.authorization.server.customize.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.demain.authorization.server.customize.domain.Oauth2UnionUser;
import com.demain.authorization.server.customize.entity.Oauth2ThirdUser;
import com.demain.authorization.server.customize.mapper.Oauth2ThirdUserMapper;
import com.demain.authorization.server.customize.service.Oauth2ThirdService;
import com.demain.authorization.server.customize.service.PlatformUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 第三方用户服务实现类
 *
 * @author demain_lee
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class Oauth2ThirdServiceImpl implements Oauth2ThirdService {
    
    private final PlatformUserService platformUserService;
    
    private final Oauth2ThirdUserMapper oauth2ThirdUserMapper;
    
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void save(Oauth2UnionUser oauth2UnionUser) {
        
        LambdaQueryWrapper<Oauth2ThirdUser> queryWrapper = Wrappers.lambdaQuery(Oauth2ThirdUser.class)
                .eq(Oauth2ThirdUser::getPlatform, oauth2UnionUser.getPlatform())
                .eq(Oauth2ThirdUser::getUniqueId, oauth2UnionUser.getUniqueId());
        
        Oauth2ThirdUser oauth2ThirdUser = oauth2ThirdUserMapper.selectOne(queryWrapper);
        
        // 数据库如果为空，则先保存到系统用户表，然后再初始化到第三方用户表
        if (oauth2ThirdUser == null) {
            Long userId = platformUserService.saveByThirdUser(oauth2UnionUser);
            Oauth2ThirdUser thirdUserDO = convertThirdUser(oauth2UnionUser);
            thirdUserDO.setUserId(userId);
            oauth2ThirdUserMapper.insert(thirdUserDO);
        } else {
            oauth2ThirdUser.setCredentialsExpiresAt(oauth2UnionUser.getCredentialsExpiresAt());
            oauth2ThirdUser.setCredentials(oauth2UnionUser.getCredentials());
            oauth2ThirdUser.setUpdateTime(LocalDateTime.now());
            oauth2ThirdUserMapper.updateById(oauth2ThirdUser);
        }
    }
    
    /**
     * 转换第三方用户信息
     *
     * @param oauth2UnionUser 第三方用户信息
     * @return 第三方用户实体
     */
    private Oauth2ThirdUser convertThirdUser(Oauth2UnionUser oauth2UnionUser) {
        Oauth2ThirdUser oauth2ThirdUserDO = new Oauth2ThirdUser();
        oauth2ThirdUserDO.setUniqueName(oauth2UnionUser.getNickName());
        oauth2ThirdUserDO.setPlatform(oauth2UnionUser.getPlatform());
        oauth2ThirdUserDO.setUniqueId(oauth2UnionUser.getUniqueId());
        oauth2ThirdUserDO.setUniqueAccount(oauth2UnionUser.getUniqueAccount());
        oauth2ThirdUserDO.setCredentials(oauth2UnionUser.getCredentials());
        oauth2ThirdUserDO.setCredentialsExpiresAt(oauth2UnionUser.getCredentialsExpiresAt());
        oauth2ThirdUserDO.setCreateTime(LocalDateTime.now());
        return oauth2ThirdUserDO;
    }
    
}
