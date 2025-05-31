package com.anji.captcha.demo.config;

import com.anji.captcha.demo.service.CaptchaCacheServiceRedisImpl;
import com.anji.captcha.properties.AjCaptchaProperties;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration//标识该类为配置类
public class CaptchaConfig {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Bean(name = "AjCaptchaCacheService")
    @Primary
    //根据AjCaptchaProperties配置，创建CaptchaCacheService实例
    public CaptchaCacheService captchaCacheService(AjCaptchaProperties config){
        //缓存类型redis/local/....
        CaptchaCacheService ret = CaptchaServiceFactory.getCache(config.getCacheType().name());
        //如果缓存类型为redis，则设置StringRedisTemplate
        if(ret instanceof CaptchaCacheServiceRedisImpl){
            ((CaptchaCacheServiceRedisImpl)ret).setStringRedisTemplate(redisTemplate);
        }
        return ret;
    }
}
