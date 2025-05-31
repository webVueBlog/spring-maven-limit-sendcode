package com.anji.captcha.service.impl;

import com.anji.captcha.model.common.Const;
import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.service.CaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ServiceLoader;

/**
 * 验证码服务工厂
 */
public class CaptchaServiceFactory {

    // 定义一个Logger对象，用于记录日志
    private static Logger logger = LoggerFactory.getLogger(CaptchaServiceFactory.class);

    // 根据配置文件获取CaptchaService实例
    public static CaptchaService getInstance(Properties config) {
        //先把所有CaptchaService初始化，通过init方法，实例字体等，add by Devli
        /*try{
            for(CaptchaService item: instances.values()){
                item.init(config);
            }
        }catch (Exception e){
            logger.warn("init captchaService fail:{}", e);
        }*/

        // 获取配置文件中captcha.type的值
        String captchaType = config.getProperty(Const.CAPTCHA_TYPE, "default");
        // 根据captcha.type的值获取对应的CaptchaService实例
        CaptchaService ret = instances.get(captchaType);
        // 如果没有对应的CaptchaService实例，则抛出异常
        if (ret == null) {
            throw new RuntimeException("unsupported-[captcha.type]=" + captchaType);
        }
        // 初始化CaptchaService实例
        ret.init(config);
        return ret;
    }

    // 根据cacheType获取CaptchaCacheService实例
    public static CaptchaCacheService getCache(String cacheType) {
        return cacheService.get(cacheType);
    }

    // 定义一个volatile修饰的Map，用于存储CaptchaService实例
    public volatile static Map<String, CaptchaService> instances = new HashMap();
    // 定义一个volatile修饰的Map，用于存储CaptchaCacheService实例
    public volatile static Map<String, CaptchaCacheService> cacheService = new HashMap();

    // 静态代码块，用于初始化instances和cacheService
    static {
        // 加载CaptchaCacheService的实现类
        ServiceLoader<CaptchaCacheService> cacheServices = ServiceLoader.load(CaptchaCacheService.class);
        // 遍历CaptchaCacheService的实现类，将其存储到cacheService中
        for (CaptchaCacheService item : cacheServices) {
            cacheService.put(item.type(), item);
        }
        // 记录支持的CaptchaCacheService
        logger.info("supported-captchaCache-service:{}", cacheService.keySet());
        // 加载CaptchaService的实现类
        ServiceLoader<CaptchaService> services = ServiceLoader.load(CaptchaService.class);
        // 遍历CaptchaService的实现类，将其存储到instances中
        for (CaptchaService item : services) {
            instances.put(item.captchaType(), item);
        }
        // 记录支持的CaptchaService
        logger.info("supported-captchaTypes-service:{}", instances.keySet());
    }
}
