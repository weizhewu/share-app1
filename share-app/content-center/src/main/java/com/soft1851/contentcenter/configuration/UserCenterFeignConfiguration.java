package com.soft1851.contentcenter.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;


public class UserCenterFeignConfiguration {
    @Bean
    public Logger.Level level() {
        //让Feign打印所有请求细节
        return Logger.Level.FULL;
    }
}
