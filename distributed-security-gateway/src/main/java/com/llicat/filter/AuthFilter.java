package com.llicat.filter;

import com.alibaba.fastjson.JSON;
import com.llicat.utils.EncryptUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: lipan
 * @date: 2021/3/7 17:29
 */

public class AuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //上下文对象用来存解析后的token信息
        RequestContext ctx = RequestContext.getCurrentContext();
        //拿到用户认证信息（包含身份信息例如用户名，与权限信息）
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null== authentication || ! (authentication instanceof OAuth2Authentication)){
            return null ;
        }


        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        //身份信息
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
        String username = userAuthentication.getName();

        List<String> authorities=new ArrayList<>();
        userAuthentication.getAuthorities().stream().forEach(ele->authorities.add(((GrantedAuthority) ele).getAuthority()));

        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
        //其他请求信息
        Map<String, String> requestParameters = oAuth2Request.getRequestParameters();

        Map<String,Object> jsonToken = new HashMap<>(requestParameters);
        if(null!=userAuthentication){
            jsonToken.put("principal",username);
            jsonToken.put("authorities",authorities);
        }

        ctx.addZuulRequestHeader("json-token", EncryptUtil.encodeUTF8StringBase64(JSON.toJSONString(jsonToken)));

        return null;
    }
}
