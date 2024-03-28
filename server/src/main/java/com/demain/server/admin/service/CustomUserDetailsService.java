package com.demain.server.admin.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.demain.server.admin.entity.CustomUserDetails;
import com.demain.server.admin.entity.PlatformUser;
import com.demain.server.admin.entity.PlatformUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义 UserDetailsService 实现
 *
 * @author demain_lee
 * @since 2024/02/27
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserDetailsService implements UserDetailsService {
    
    private final PlatformUserService userService;
    
    private final PlatformUserRoleService userRoleService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PlatformUser platformUser =
                userService.getOne(Wrappers.<PlatformUser>lambdaQuery().eq(PlatformUser::getAccount, username));
        if (platformUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        List<PlatformUserRole> userRoleList = userRoleService.list(Wrappers.<PlatformUserRole>lambdaQuery()
                .in(PlatformUserRole::getUserId, platformUser.getId()));
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUsername(platformUser.getAccount());
        customUserDetails.setPassword(platformUser.getPassword());
        customUserDetails.setStatus(platformUser.getStatus());
        customUserDetails.setRoleList(userRoleList);
        return customUserDetails;
    }
}
