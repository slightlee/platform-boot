package com.demain.platform.auth.exception.oauth;

import com.demain.platform.core.util.Result;
import com.demain.platform.core.util.ResultEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * 自定义异常翻译器
 * @author: 明天
 * @since: 2022/3/22 15:51
 */
@SuppressWarnings("ALL")
public class OAuthServerWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity<Result> translate(Exception e) throws Exception {
        Result result = doTranslateHandler(e);
        return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 根据异常定制返回信息
     */
    private Result doTranslateHandler(Exception e) {
        //初始值，系统错误，
        ResultEnum resultEnum = ResultEnum.UNAUTHORIZED;
        //判断异常，不支持的认证方式
        if(e instanceof UnsupportedGrantTypeException){
            resultEnum = ResultEnum.UNSUPPORTED_GRANT_TYPE;
            //用户名或密码异常
        }else if(e instanceof InvalidGrantException){
            resultEnum = ResultEnum.ACCOUNT_PASSWORD_IS_ERROR;
        }
        return Result.error(resultEnum.getCode(),resultEnum.getMsg());
    }
}
