package com.demain.authorization.server.customize.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.demain.authorization.server.customize.entity.PlatformMenu;
import com.demain.authorization.server.customize.entity.PlatformUser;
import com.demain.authorization.server.customize.mapper.PlatformMenuMapper;
import com.demain.authorization.server.customize.mapper.PlatformUserMapper;
import com.demain.authorization.server.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final PlatformUserMapper platformUserMapper;
    
    private final PlatformMenuMapper platformMenuMapper;
    
    /**
     * UserDetailsService 另一种实现，用于检索要进行身份验证的用户
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据 账号 或者 手机号 或者 邮箱 查询用户信息
        PlatformUser platformUser = platformUserMapper.selectOne(Wrappers.<PlatformUser>lambdaQuery()
                .eq(PlatformUser::getAccount, username)
                .or()
                .eq(PlatformUser::getPhone, username)
                .or()
                .eq(PlatformUser::getEmail, username)
                .eq(PlatformUser::getIsDelete, 0));
        if (platformUser == null) {
            throw new UsernameNotFoundException(ResponseCode.USER_ACCOUNT_NOT_FOUND.getMessage());
        }
        List<PlatformMenu> platformMenuList = platformMenuMapper.menuListByUserId(platformUser.getId());
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities =
                platformMenuList.stream().map(authority -> new SimpleGrantedAuthority(authority.getUrl())).collect(
                        Collectors.toSet());
        return new User(platformUser.getAccount(), platformUser.getPassword(), simpleGrantedAuthorities);
    }
    
}
