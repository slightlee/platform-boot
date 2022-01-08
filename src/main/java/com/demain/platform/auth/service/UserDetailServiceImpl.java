package com.demain.platform.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.demain.platform.admin.entity.PlatformUser;
import com.demain.platform.admin.mapper.PlatformUserMapper;
import com.demain.platform.auth.entity.PlatformUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDetailServiceImpl implements UserDetailsService {

    private final PlatformUserMapper platformUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        PlatformUser user = platformUserMapper.selectOne(new LambdaQueryWrapper<PlatformUser>().eq(PlatformUser::getAccount, username));

        if (user != null) {

            return new PlatformUserDetails(
                    user.getId(), user.getTenantId(),
                    user.getNickname(), user.getRealName(),
                    user.getAvatar(), user.getAccount(), user.getPassword(), !String.valueOf(0).equals(user.getStatus()), true, true, true,
                    AuthorityUtils.createAuthorityList("USER")
            );
        } else {
            throw new UsernameNotFoundException("用户[" + username + "]不存在");
        }
    }
}
