package com.anji.captcha.service.impl;

import com.anji.captcha.service.CaptchaCacheService;
import com.anji.captcha.util.CacheUtil;

/**
 * @Title: 默认使用内存当缓存
 */
public class CaptchaCacheServiceMemImpl implements CaptchaCacheService {
    // 设置缓存
    @Override
    public void set(String key, String value, long expiresInSeconds) {
        CacheUtil.set(key, value, expiresInSeconds);
    }

    // 判断缓存是否存在
    @Override
    public boolean exists(String key) {
        return CacheUtil.exists(key);
    }

    // 删除缓存
    @Override
    public void delete(String key) {
        CacheUtil.delete(key);
    }

    // 获取缓存
    @Override
    public String get(String key) {
        return CacheUtil.get(key);
    }

    // 增加缓存值
    @Override
    public Long increment(String key, long val) {
        long ret;
        String value = CacheUtil.get(key);
        if (null == value) {
            ret = val;
        } else {
            ret = Long.parseLong(value) + val;
        }
        CacheUtil.set(key, ret + "", 0);
        return ret;
    }

    // 设置缓存过期时间
    @Override
    public void setExpire(String key, long l) {
        CacheUtil.setExpire(key, l);
    }

    // 获取缓存类型
    @Override
    public String type() {
        return "local";
    }
}
