
package com.anji.captcha.service;

/**
 * 验证码缓存接口
 */
public interface CaptchaCacheService {

	// 设置缓存
	void set(String key, String value, long expiresInSeconds);

	// 判断缓存是否存在
	boolean exists(String key);

	// 删除缓存
	void delete(String key);

	// 获取缓存
	String get(String key);

	/**
	 * 缓存类型-local/redis/memcache/..
	 * 通过java SPI机制，接入方可自定义实现类
	 * @return
	 */
	String type();

	/***
	 * 递增缓存
	 * @param key
	 * @param val
	 * @return
	 */
	// 默认的递增方法，返回0L
	default Long increment(String key, long val){
		return 0L;
	};

	// 设置缓存过期时间
	void setExpire(String key, long l);

}
