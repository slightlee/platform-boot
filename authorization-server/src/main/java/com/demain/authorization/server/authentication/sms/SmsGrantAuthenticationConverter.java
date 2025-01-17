package com.demain.authorization.server.authentication.sms;

import com.demain.authorization.server.constant.SecurityConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取请求中参数转化为SmsGrantAuthenticationToken
 *
 * @author demain_lee
 * @since 2025/01/08
 */
public class SmsGrantAuthenticationConverter implements AuthenticationConverter {
    
    @Override
    public Authentication convert(HttpServletRequest request) {
        
        // grant_type (REQUIRED)
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!SecurityConstants.GRANT_TYPE_SMS.equals(grantType)) {
            return null;
        }
        
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
        
        MultiValueMap<String, String> parameters = getParameters(request);
        
        String smsCode = parameters.getFirst(SecurityConstants.SMS_VERIFICATION_CODE);
        if (!StringUtils.hasText(smsCode) || parameters.get(SecurityConstants.SMS_VERIFICATION_CODE).size() != 1) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_REQUEST);
        }
        
        // 收集要传入SmsGrantAuthenticationToken构造方法的参数，该参数接下来在SmsGrantAuthenticationProvider中使用
        Map<String, Object> additionalParameters = new HashMap<>();
        parameters.forEach((key, values) -> {
            // 遍历从request中提取的参数，排除掉grant_type、client_id、code等字段参数，其他参数收集到additionalParameters中
            if (!key.equals(OAuth2ParameterNames.GRANT_TYPE) &&
                    !key.equals(OAuth2ParameterNames.CLIENT_ID) &&
                    !key.equals(OAuth2ParameterNames.CODE)) {
                additionalParameters.put(key, values.get(0));
            }
        });
        
        return new SmsGrantAuthenticationToken(clientPrincipal, additionalParameters);
        
    }
    
    /**
     * 从request中提取请求参数，然后存入MultiValueMap<String, String>
     */
    private static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            for (String value : values) {
                parameters.add(key, value);
            }
        });
        return parameters;
    }
}
