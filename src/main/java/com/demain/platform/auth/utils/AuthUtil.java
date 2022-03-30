package com.demain.platform.auth.utils;

import com.demain.platform.auth.entity.AuthUser;
import com.demain.platform.core.contant.Constants;
import com.demain.platform.core.exception.PlatformException;
import com.demain.platform.core.util.FuncUtil;
import com.demain.platform.core.util.ResultEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;


@Slf4j
public class AuthUtil {


    /**
     * 获取用户信息
     */
    public static AuthUser getUser() {
        HttpServletRequest request = getRequest();
        String token = getRequestToken(request);
        Claims claims = Jwts.parser().setSigningKey(Constants.SIGN_KEY.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        String clientId = FuncUtil.toStr(claims.get("client_id"));
        Long userId = FuncUtil.toLong(claims.get("userId"));
        String account = FuncUtil.toStr(claims.get("account"));
        String realName = FuncUtil.toStr(claims.get("realName"));
        return AuthUser.builder()
                .clientId(clientId)
                .userId(userId)
                .account(account)
                .realName(realName)
                .build();
    }


    /**
     * 获取用户ID
     **/
    public static Long getUserId() {
        return getUser().getUserId();
    }


    /**
     * 获取请求对象中的token数据
     */
    public static String getRequestToken(HttpServletRequest request) {
        // 获取JwtTokens失败
        String authorization = request.getHeader("authorization");
        log.info("token=========>" + authorization);
        if (authorization == null || !authorization.startsWith(Constants.JWT_TOKEN_PREFIX)) {
            throw new PlatformException(ResultEnum.TOKEN_INVALID);
        }
        //因为有前缀，所以要去掉前缀
        return authorization.substring(7);
    }


    /**
     * 获取ServletRequestAttributes对象
     */
    public static ServletRequestAttributes getServletRequest(){
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 获取HttpServletRequest对象
     */
    public static HttpServletRequest getRequest(){
        return getServletRequest().getRequest();
    }


    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
