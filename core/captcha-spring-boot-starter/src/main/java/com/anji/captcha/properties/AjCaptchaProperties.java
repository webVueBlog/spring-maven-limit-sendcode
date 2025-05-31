package com.anji.captcha.properties;

import com.anji.captcha.model.common.CaptchaTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.awt.*;

import static com.anji.captcha.properties.AjCaptchaProperties.PREFIX;
import static com.anji.captcha.properties.AjCaptchaProperties.StorageType.local;

@ConfigurationProperties(PREFIX)//前缀
public class AjCaptchaProperties {//配置类
    public static final String PREFIX = "aj.captcha";//前缀

    /**
     * 验证码类型.
     */
    private CaptchaTypeEnum type = CaptchaTypeEnum.DEFAULT;

    /**
     * 滑动拼图底图路径.
     */
    private String jigsaw = "";

    /**
     * 点选文字底图路径.
     */
    private String picClick = "";

    /**
     * 右下角水印文字(水印文字).
     */
    private String waterMark = "水印文字";

    /**
     * 右下角水印字体(文泉驿正黑).
     */
    private String waterFont = "WenQuanZhengHei.ttf";

    /**
     * 点选文字验证码的文字字体(文泉驿正黑).
     */
    private String fontType = "WenQuanZhengHei.ttf";

    /**
     * 校验滑动拼图允许误差偏移量(默认5像素).
     */
    private String slipOffset = "5";

    /**
     * aes加密坐标开启或者禁用(true|false).
     */
    private Boolean aesStatus = true;

    /**
     * 滑块干扰项(0/1/2)
     */
    private String interferenceOptions = "0";

    /**
     * local缓存的阈值
     */
    private String cacheNumber = "1000";

    /**
     * 定时清理过期local缓存(单位秒)
     */
    private String timingClear = "180";

    /**
     * 缓存类型redis/local/....
     */
    private StorageType cacheType = local;
    /**
     * 历史数据清除开关
     */
    private boolean historyDataClearEnable = false;

    /**
     * 一分钟内接口请求次数限制 开关
     */
    private boolean reqFrequencyLimitEnable = false;

    /***
     * 一分钟内check接口失败次数
     */
    private int reqGetLockLimit = 5;

    /**
     * get接口锁定时间(秒)
     */
    private int reqGetLockSeconds = 300;

    /***
     * get接口一分钟内限制访问数
     */
    private int reqGetMinuteLimit = 100;
    /**
     * 一分钟内check接口限制访问数
     */
    private int reqCheckMinuteLimit = 100;
    /**
     * 一分钟内接口请求次数限制
     */
    private int reqVerifyMinuteLimit = 100;

    /**
     * 点选字体样式
     */
    private int fontStyle = Font.BOLD;

    /**
     * 点选字体大小
     */
    private int fontSize = 25;

    /**
     * 点选文字个数，存在问题，暂不要使用
     */
    private int clickWordCount = 4;

    public int getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(int fontStyle) {
        this.fontStyle = fontStyle;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getClickWordCount() {
        return clickWordCount;
    }

    public void setClickWordCount(int clickWordCount) {
        this.clickWordCount = clickWordCount;
    }

    public boolean isHistoryDataClearEnable() {
        return historyDataClearEnable;
    }

    public void setHistoryDataClearEnable(boolean historyDataClearEnable) {
        this.historyDataClearEnable = historyDataClearEnable;
    }

    public boolean isReqFrequencyLimitEnable() {
        return reqFrequencyLimitEnable;
    }

    public boolean getReqFrequencyLimitEnable() {
        return reqFrequencyLimitEnable;
    }

    public void setReqFrequencyLimitEnable(boolean reqFrequencyLimitEnable) {
        this.reqFrequencyLimitEnable = reqFrequencyLimitEnable;
    }

    public int getReqGetLockLimit() {
        return reqGetLockLimit;
    }

    public void setReqGetLockLimit(int reqGetLockLimit) {
        this.reqGetLockLimit = reqGetLockLimit;
    }

    public int getReqGetLockSeconds() {
        return reqGetLockSeconds;
    }

    public void setReqGetLockSeconds(int reqGetLockSeconds) {
        this.reqGetLockSeconds = reqGetLockSeconds;
    }

    public int getReqGetMinuteLimit() {
        return reqGetMinuteLimit;
    }

    public void setReqGetMinuteLimit(int reqGetMinuteLimit) {
        this.reqGetMinuteLimit = reqGetMinuteLimit;
    }

    public int getReqCheckMinuteLimit() {
        return reqGetMinuteLimit;
    }

    public void setReqCheckMinuteLimit(int reqCheckMinuteLimit) {
        this.reqCheckMinuteLimit = reqCheckMinuteLimit;
    }

    public int getReqVerifyMinuteLimit() {
        return reqVerifyMinuteLimit;
    }

    public void setReqVerifyMinuteLimit(int reqVerifyMinuteLimit) {
        this.reqVerifyMinuteLimit = reqVerifyMinuteLimit;
    }

    public enum StorageType {
        /**
         * 内存.
         */
        local,
        /**
         * redis.
         */
        redis,
        /**
         * 其他.
         */
        other,
    }

    public static String getPrefix() {
        return PREFIX;
    }

    public CaptchaTypeEnum getType() {
        return type;
    }

    public void setType(CaptchaTypeEnum type) {
        this.type = type;
    }

    public String getJigsaw() {
        return jigsaw;
    }

    public void setJigsaw(String jigsaw) {
        this.jigsaw = jigsaw;
    }

    public String getPicClick() {
        return picClick;
    }

    public void setPicClick(String picClick) {
        this.picClick = picClick;
    }

    public String getWaterMark() {
        return waterMark;
    }

    public void setWaterMark(String waterMark) {
        this.waterMark = waterMark;
    }

    public String getWaterFont() {
        return waterFont;
    }

    public void setWaterFont(String waterFont) {
        this.waterFont = waterFont;
    }

    public String getFontType() {
        return fontType;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
    }

    public String getSlipOffset() {
        return slipOffset;
    }

    public void setSlipOffset(String slipOffset) {
        this.slipOffset = slipOffset;
    }

    public Boolean getAesStatus() {
        return aesStatus;
    }

    public void setAesStatus(Boolean aesStatus) {
        this.aesStatus = aesStatus;
    }

    public StorageType getCacheType() {
        return cacheType;
    }

    public void setCacheType(StorageType cacheType) {
        this.cacheType = cacheType;
    }

    public String getInterferenceOptions() {
        return interferenceOptions;
    }

    public void setInterferenceOptions(String interferenceOptions) {
        this.interferenceOptions = interferenceOptions;
    }

    public String getCacheNumber() {
        return cacheNumber;
    }

    public void setCacheNumber(String cacheNumber) {
        this.cacheNumber = cacheNumber;
    }

    public String getTimingClear() {
        return timingClear;
    }

    public void setTimingClear(String timingClear) {
        this.timingClear = timingClear;
    }

    @Override
    public String toString() {
        return "\nAjCaptchaProperties{" +
                "type=" + type + // 类型
                ", jigsaw='" + jigsaw + '\'' + // 拼图
                ", picClick='" + picClick + '\'' + // 图片点击
                ", waterMark='" + waterMark + '\'' + // 水印
                ", waterFont='" + waterFont + '\'' + // 水印字体
                ", fontType='" + fontType + '\'' + // 字体类型
                ", slipOffset='" + slipOffset + '\'' + // 滑动偏移
                ", aesStatus=" + aesStatus + // AES状态
                ", interferenceOptions='" + interferenceOptions + '\'' + // 干扰选项
                ", cacheNumber='" + cacheNumber + '\'' + // 缓存数量
                ", timingClear='" + timingClear + '\'' + // 定时清除
                ", cacheType=" + cacheType + // 缓存类型
                ", reqFrequencyLimitEnable=" + reqFrequencyLimitEnable + // 请求频率限制启用
                ", reqGetLockLimit=" + reqGetLockLimit + // 请求获取锁限制
                ", reqGetLockSeconds=" + reqGetLockSeconds + // 请求获取锁秒数
                ", reqGetMinuteLimit=" + reqGetMinuteLimit + // 请求获取分钟限制
                ", reqCheckMinuteLimit=" + reqCheckMinuteLimit + // 请求检查分钟限制
                ", reqVerifyMinuteLimit=" + reqVerifyMinuteLimit + // 请求验证分钟限制
                '}';
    }
}
