package com.soft1851.contentcenter.feignclient;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class TokenInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template){
        //1．获取到token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader( "X-Token");
        //2．将token传递
        if (StringUtils.isNotBlank(token)) {
            template.header("X-Token", token);
        }
    }
}
