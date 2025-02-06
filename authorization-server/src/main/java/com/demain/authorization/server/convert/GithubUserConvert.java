package com.demain.authorization.server.convert;

import com.demain.authorization.server.customize.domain.Oauth2UnionUser;
import com.demain.authorization.server.customize.enums.ThirdPlatFormEnum;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Optional;

/**
 * Github 用户转换
 *
 * @author demain_lee
 * @since 0.0.1
 */
public class GithubUserConvert implements OAuth2UserConvert {
    
    private final static String AVATAR_URL = "avatar_url";
    private final static String UNIQUE_ID = "id";
    private final static String NAME = "name";
    private final static String ACCOUNT = "login";
    private final static String EMAIL = "email";
    
    @Override
    public Oauth2UnionUser convert(OAuth2User oAuth2User) {
        
        // 获取三方用户信息
        String avatarUrl = Optional.ofNullable(oAuth2User.getAttribute(AVATAR_URL)).map(Object::toString).orElse(null);
        String uniqueId = Optional.ofNullable(oAuth2User.getAttribute(UNIQUE_ID)).map(Object::toString).orElse(null);
        String uniqueAccount = Optional.ofNullable(oAuth2User.getAttribute(ACCOUNT)).map(Object::toString).orElse(null);
        String email = Optional.ofNullable(oAuth2User.getAttribute(EMAIL)).map(Object::toString).orElse(null);
        String nickName = Optional.ofNullable(oAuth2User.getAttribute(NAME)).map(Object::toString).orElse(null);
        
        // 转换至 Oauth2ThirdAccount
        Oauth2UnionUser unionUser = new Oauth2UnionUser();
        unionUser.setUniqueId(uniqueId);
        unionUser.setUniqueAccount(uniqueAccount);
        unionUser.setAvatarUrl(avatarUrl);
        unionUser.setNickName(nickName);
        unionUser.setEmail(email);
        unionUser.setPlatform(ThirdPlatFormEnum.GITHUB.name());
        
        return unionUser;
    }
    
}
