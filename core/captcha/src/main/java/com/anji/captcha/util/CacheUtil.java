
package com.anji.captcha.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

public final class CacheUtil {
    // 定义一个静态的Logger对象，用于记录日志
    private static final Logger logger = LoggerFactory.getLogger(CacheUtil.class);

    // 定义一个静态的Map对象，用于存储缓存数据
    private static final Map<String, Object> CACHE_MAP = new ConcurrentHashMap<String, Object>();

    /**
     * 缓存最大个数
     */
    private static Integer CACHE_MAX_NUMBER = 1000;

    /**
     * 初始化
     *
     * @param cacheMaxNumber 缓存最大个数
     * @param second         定时任务 秒执行清除过期缓存
     */
    public static void init(int cacheMaxNumber, long second) {
        // 初始化缓存最大数量
        CACHE_MAX_NUMBER = cacheMaxNumber;
        if (second > 0L) {
            // 如果second大于0，则创建一个定时器，每隔second秒执行一次refresh()方法
            /*Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    refresh();
                }
            }, 0, second * 1000);*/
            // 创建一个ScheduledThreadPoolExecutor，用于定时执行refresh()方法
            scheduledExecutor = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "thd-captcha-cache-clean");
                }
            }, new ThreadPoolExecutor.CallerRunsPolicy());
            // 每隔second秒执行一次refresh()方法
            scheduledExecutor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    refresh();
                }
            }, 10, second, TimeUnit.SECONDS);

            // 在程序退出时，关闭定时器
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    if (Objects.nonNull(scheduledExecutor)) {
                        clear();
                        scheduledExecutor.shutdownNow();
                    }
                }
            }));
        }
    }

    private static ScheduledExecutorService scheduledExecutor;

    /**
     * 缓存刷新,清除过期数据
     */
    public static void refresh() {
        logger.debug("local缓存刷新,清除过期数据");
        for (String key : CACHE_MAP.keySet()) {
            exists(key);
        }
    }


    public static void set(String key, String value, long expiresInSeconds) {
        //设置阈值，达到即clear缓存
        //判断缓存大小是否超过阈值，如果超过则清除缓存
        if (CACHE_MAP.size() > CACHE_MAX_NUMBER * 2) {
            logger.info("CACHE_MAP达到阈值，clear map");
            clear();
        }
        //将键值对存入缓存
        CACHE_MAP.put(key, value);
        //如果设置了缓存失效时间，则将失效时间存入缓存
        if (expiresInSeconds > 0) {
            CACHE_MAP.put(key + "_HoldTime", System.currentTimeMillis() + expiresInSeconds * 1000);//缓存失效时间
        }
    }

    // 根据key删除缓存
    public static void delete(String key) {
        CACHE_MAP.remove(key);
        CACHE_MAP.remove(key + "_HoldTime");
    }

    // 判断缓存中是否存在指定key
    public static boolean exists(String key) {
        Long cacheHoldTime = (Long) CACHE_MAP.get(key + "_HoldTime");
        // 如果缓存持有时间为空或为0，则返回false
        if (cacheHoldTime == null || cacheHoldTime == 0L) {
            return false;
        }
        // 如果缓存持有时间小于当前时间，则删除该缓存，并返回false
        if (cacheHoldTime < System.currentTimeMillis()) {
            delete(key);
            return false;
        }
        return true;
    }


    // 根据key获取缓存中的值
    public static String get(String key) {
        if (exists(key)) {
            // 如果存在，则返回缓存中的值
            return (String) CACHE_MAP.get(key);
        }
        return null;
    }

    /**
     * 删除所有缓存
     */
    public static void clear() {
        logger.debug("have clean all key !");
        CACHE_MAP.clear();
    }

    /**
     * 设置过期时间
     */
    public static void setExpire(String key, long expiresInSeconds) {
        CACHE_MAP.put(key + "_HoldTime", System.currentTimeMillis() + expiresInSeconds * 1000);//缓存失效时间
    }
}
