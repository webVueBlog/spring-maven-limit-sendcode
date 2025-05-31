package com.anji.captcha.config;

import com.anji.captcha.properties.AjCaptchaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration//注解告诉Spring这是一个配置类
@EnableConfigurationProperties(AjCaptchaProperties.class)//开启属性注入
@ComponentScan("com.anji.captcha")//扫描指定包下的类
@Import({AjCaptchaServiceAutoConfiguration.class, AjCaptchaStorageAutoConfiguration.class})//导入其他配置类
public class AjCaptchaAutoConfiguration {// 配置类
}
