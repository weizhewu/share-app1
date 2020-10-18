package com.soft1851.contentcenter.configuration;

import org.apache.catalina.filters.CorsFilter;
import org.checkerframework.checker.units.qual.C;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class AjaxCorsFilter  {
//    public AjaxCorsFilter() {
//        super(configurationSource());
//        CorsFilter corsFilter = new CorsFilter();
//        corsFilter.
//    }
//    private static UrlBasedCorsConfigurationSource configurationSource() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        List<String> allowedHeaders = Arrays.asList("x-auth-token", "content-type", "X-Requested-With", "XMLHttpRequest");
//        List<String> exposedHeaders = Arrays.asList("x-auth-token", "content-type", "X-Requested-With", "XMLHttpRequest");
//        List<String> allowedMethods = Arrays.asList("POST", "GET", "DELETE", "PUT", "OPTIONS");
//        List<String> allowedOrigins = Arrays.asList("*");
//        corsConfig.setAllowedHeaders(allowedHeaders);
//        corsConfig.setAllowedMethods(allowedMethods);
//        corsConfig.setAllowedOrigins(allowedOrigins);
//        corsConfig.setExposedHeaders(exposedHeaders);
//        corsConfig.setMaxAge(36000L);
//        corsConfig.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);
//        return source;
//    }
}