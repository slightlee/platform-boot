package com.demain.authorization.server.authentication.captcha;

import com.demain.authorization.server.constant.SecurityConstants;
import com.demain.authorization.server.exception.InvalidCaptchaException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Slf4j
@Component
public class CaptchaAuthenticationProvider extends DaoAuthenticationProvider {
    
    /**
     * 利用构造方法在通过{@link Component}注解初始化时 注入UserDetailsService和passwordEncoder，然后 设置调用父类关于这两个属性的set方法设置进去
     *
     * @param userDetailsService 用户服务，用于提供用户信息
     * @param passwordEncoder 密码解析器，用于加密和校验密码
     */
    public CaptchaAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        super.setPasswordEncoder(passwordEncoder);
        super.setUserDetailsService(userDetailsService);
    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        log.info("Authenticate captcha...");
        
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new InvalidCaptchaException("Failed to get the current request.");
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        
        // 获取当前登录方式
        String loginType = request.getParameter("loginType");
        if (!Objects.equals(loginType, SecurityConstants.PASSWORD_LOGIN_TYPE)) {
            // 只要不是密码登录都不需要校验图形验证码
            log.info("It isn't necessary captcha authenticate.");
            return super.authenticate(authentication);
        }
        
        // 获取参数中的验证码
        String code = request.getParameter("code");
        if (ObjectUtils.isEmpty(code)) {
            throw new InvalidCaptchaException("The captcha cannot be empty.");
        }
        
        // 获取session中存储的验证码
        Object sessionCaptcha = request.getSession(Boolean.FALSE).getAttribute("captcha");
        if (sessionCaptcha instanceof String sessionCode) {
            if (!sessionCode.equalsIgnoreCase(code)) {
                throw new InvalidCaptchaException("The captcha is incorrect（验证码不正确）.");
            }
        } else {
            throw new InvalidCaptchaException("The captcha is abnormal. Obtain it again.");
        }
        
        log.info("Captcha authenticated.");
        return super.authenticate(authentication);
    }
}
