package com.anji.captcha.config;


import com.anji.captcha.model.common.Const;
import com.anji.captcha.properties.AjCaptchaProperties;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.service.impl.CaptchaServiceFactory;
import com.anji.captcha.util.ImageUtils;
import com.anji.captcha.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class AjCaptchaServiceAutoConfiguration {

    // 定义一个静态的Logger对象，用于记录日志
    private static Logger logger = LoggerFactory.getLogger(AjCaptchaServiceAutoConfiguration.class);

    // 定义一个Bean，如果容器中不存在该Bean，则创建该Bean
    @Bean
    // 如果容器中不存在该Bean，则创建该Bean
    @ConditionalOnMissingBean
    public CaptchaService captchaService(AjCaptchaProperties prop) {
        logger.info("自定义配置项：{}", prop.toString());
        Properties config = new Properties();
        // 设置验证码缓存类型
        config.put(Const.CAPTCHA_CACHETYPE, prop.getCacheType().name());
        // 设置验证码水印
        config.put(Const.CAPTCHA_WATER_MARK, prop.getWaterMark());
        // 设置验证码字体类型
        config.put(Const.CAPTCHA_FONT_TYPE, prop.getFontType());
        // 设置验证码类型
        config.put(Const.CAPTCHA_TYPE, prop.getType().getCodeValue());
        // 设置验证码干扰选项
        config.put(Const.CAPTCHA_INTERFERENCE_OPTIONS, prop.getInterferenceOptions());
        // 设置拼图底图路径
        config.put(Const.ORIGINAL_PATH_JIGSAW, prop.getJigsaw());
        // 设置图片点击验证码底图路径
        config.put(Const.ORIGINAL_PATH_PIC_CLICK, prop.getPicClick());
        // 设置验证码滑块偏移量
        config.put(Const.CAPTCHA_SLIP_OFFSET, prop.getSlipOffset());
        // 设置验证码AES加密状态
        config.put(Const.CAPTCHA_AES_STATUS, String.valueOf(prop.getAesStatus()));
        // 设置验证码水印字体
        config.put(Const.CAPTCHA_WATER_FONT, prop.getWaterFont());
        // 设置验证码缓存最大数量
        config.put(Const.CAPTCHA_CACAHE_MAX_NUMBER, prop.getCacheNumber());
        // 设置验证码定时清除时间
        config.put(Const.CAPTCHA_TIMING_CLEAR_SECOND, prop.getTimingClear());

        // 设置历史数据清除状态
        config.put(Const.HISTORY_DATA_CLEAR_ENABLE, prop.isHistoryDataClearEnable() ? "1" : "0");

        // 设置请求频率限制状态
        config.put(Const.REQ_FREQUENCY_LIMIT_ENABLE, prop.getReqFrequencyLimitEnable() ? "1" : "0");
        // 设置请求获取锁限制
        config.put(Const.REQ_GET_LOCK_LIMIT, prop.getReqGetLockLimit() + "");
        // 设置请求获取锁时间
        config.put(Const.REQ_GET_LOCK_SECONDS, prop.getReqGetLockSeconds() + "");
        // 设置请求获取分钟限制
        config.put(Const.REQ_GET_MINUTE_LIMIT, prop.getReqGetMinuteLimit() + "");
        // 设置请求检查分钟限制
        config.put(Const.REQ_CHECK_MINUTE_LIMIT, prop.getReqCheckMinuteLimit() + "");
        // 设置请求验证分钟限制
        config.put(Const.REQ_VALIDATE_MINUTE_LIMIT, prop.getReqVerifyMinuteLimit() + "");

        // 设置验证码字体大小
        config.put(Const.CAPTCHA_FONT_SIZE, prop.getFontSize() + "");
        // 设置验证码字体样式
        config.put(Const.CAPTCHA_FONT_STYLE, prop.getFontStyle() + "");
        // 设置验证码点击字数
        config.put(Const.CAPTCHA_WORD_COUNT, prop.getClickWordCount() + "");

        // 如果底图路径以classpath开头，则初始化底图
        if ((StringUtils.isNotBlank(prop.getJigsaw()) && prop.getJigsaw().startsWith("classpath"))
                || (StringUtils.isNotBlank(prop.getPicClick()) && prop.getPicClick().startsWith("classpath"))) {
            //自定义resources目录下初始化底图
            config.put(Const.CAPTCHA_INIT_ORIGINAL, "true");
            initializeBaseMap(prop.getJigsaw(), prop.getPicClick());
        }
        // 创建验证码服务实例
        CaptchaService s = CaptchaServiceFactory.getInstance(config);
        return s;
    }

    // 初始化基础地图
    private static void initializeBaseMap(String jigsaw, String picClick) {
        // 缓存启动图片
        ImageUtils.cacheBootImage(getResourcesImagesFile(jigsaw + "/original/*.png"),
                getResourcesImagesFile(jigsaw + "/slidingBlock/*.png"),
                getResourcesImagesFile(picClick + "/*.png"));
    }

    // 根据路径获取资源图片文件
    public static Map<String, String> getResourcesImagesFile(String path) {
        // 用于存储图片文件名和Base64编码的图片字符串
        Map<String, String> imgMap = new HashMap<>();
        // 用于解析路径
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            // 根据路径获取资源
            Resource[] resources = resolver.getResources(path);
            // 遍历资源
            for (Resource resource : resources) {
                // 将资源转换为字节数组
                byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
                // 将字节数组转换为Base64编码的字符串
                String string = Base64Utils.encodeToString(bytes);
                String filename = resource.getFilename();
                // 将文件名和Base64编码的图片字符串存入Map中
                imgMap.put(filename, string);
            }
        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
        }
        return imgMap;
    }
}
