package com.llicat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author: lipan
 * @date: 2021/3/6 17:32
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true) //为了使方法上的权限拦截生效
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    //安全拦截机制（最重要）
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                            .antMatchers("/r/**").authenticated()
                            .anyRequest().permitAll();

    }

}
