package com.anji.captcha.config;

import com.anji.captcha.properties.AjCaptchaProperties;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 存储策略自动配置.
 */
@Configuration
public class AjCaptchaStorageAutoConfiguration {

    @Bean(name = "AjCaptchaCacheService")
    //如果容器中没有该Bean，则创建
    @ConditionalOnMissingBean
    //注入AjCaptchaProperties配置
    public CaptchaCacheService captchaCacheService(AjCaptchaProperties config){
        //缓存类型redis/local/....
        return CaptchaServiceFactory.getCache(config.getCacheType().name());
    }
}
