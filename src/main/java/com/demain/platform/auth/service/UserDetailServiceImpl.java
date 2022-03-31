package com.demain.platform.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.demain.platform.admin.entity.PlatformUser;
import com.demain.platform.admin.mapper.PlatformUserMapper;
import com.demain.platform.auth.entity.PlatformUserDetails;
import com.demain.platform.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDetailServiceImpl implements UserDetailsService {

    private final PlatformUserMapper platformUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        PlatformUser user = platformUserMapper.selectOne(new LambdaQueryWrapper<PlatformUser>().eq(PlatformUser::getAccount, username));

        if (ObjectUtil.isNotEmpty(user)) {
            List<String> roleList = platformUserMapper.roleKeyList(user.getId());
            String authorities = ObjectUtil.isNotEmpty(roleList) ? StringUtils.collectionToCommaDelimitedString(roleList) : "";
            return new PlatformUserDetails(
                    user.getId(), user.getTenantId(),
                    user.getNickname(), user.getRealName(),
                    user.getAvatar(), user.getAccount(), user.getPassword(), true, true, true, user.getStatus() == 0,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(authorities)
            );
        } else {
            throw new UsernameNotFoundException("用户[" + username + "]不存在");
        }
    }
}
