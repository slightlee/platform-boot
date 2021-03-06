package com.demain.platform.auth.config;

import com.demain.platform.auth.enhancer.CustomTokenEnhancer;
import com.demain.platform.auth.exception.oauth.OAuthServerAuthenticationEntryPoint;
import com.demain.platform.auth.exception.oauth.OAuthServerWebResponseExceptionTranslator;
import com.demain.platform.auth.filter.OAuthServerClientCredentialsTokenEndpointFilter;
import com.demain.platform.core.contant.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * ??????????????????
 *
 * @Author ??????
 * @Date 2022/1/8 15:14
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final DataSource dataSource;

    private final CustomTokenEnhancer customTokenEnhancer;

    private final OAuthServerAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        //?????????ClientCredentialsTokenEndpointFilter????????????????????????id????????????????????????
        OAuthServerClientCredentialsTokenEndpointFilter endpointFilter = new OAuthServerClientCredentialsTokenEndpointFilter(security,authenticationEntryPoint);
        endpointFilter.afterPropertiesSet();
        security.addTokenEndpointAuthenticationFilter(endpointFilter);


        security
                .authenticationEntryPoint(authenticationEntryPoint)
                // ???????????????????????????
//                .allowFormAuthenticationForClients()
                // ????????????/oauth/token_key???????????????????????????
                .tokenKeyAccess("permitAll()")
                // ????????????/oauth/check_token???????????????????????????
                .checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // ???????????????
        clients.withClientDetails(clientDetails());
    }


    @Bean
    public ClientDetailsService clientDetails() {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        // ???????????? bcrypt ????????????
        jdbcClientDetailsService.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
        return jdbcClientDetailsService;
    }

    @Override
    @SuppressWarnings("ALL")
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(customTokenEnhancer);
        tokenEnhancers.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);

        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain)
                .exceptionTranslator(new OAuthServerWebResponseExceptionTranslator())
        ;
    }

    /**
     * ?????? tokenStore?????????????????????????????? token?????????????????????
     */
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * ????????????????????????????????????
     */
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // ????????????
        jwtAccessTokenConverter.setSigningKey(Constants.SIGN_KEY);
        // ??????????????????????????????????????????????????????
        jwtAccessTokenConverter.setVerifier(new MacSigner(Constants.SIGN_KEY));
        return jwtAccessTokenConverter;
    }


}

