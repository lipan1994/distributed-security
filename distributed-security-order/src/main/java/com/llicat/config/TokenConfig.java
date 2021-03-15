package com.llicat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 令牌存储策略
 * @author: lipan
 * @date: 2021/3/1 20:37
 */

@Configuration
public class TokenConfig {


    /**
     * 秘钥
     */
    private String SIGNING_KEY = "uaa123";

//    @Bean
//    public TokenStore tokenStore(){
//
//        return new InMemoryTokenStore();
//    }



    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * 转换jwt令牌 token->用户信息
     * @return
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY); //对称秘钥，资源服务器使用该秘钥来验证
         return converter;
    }
    }

