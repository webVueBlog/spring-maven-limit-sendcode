package com.anji.captcha.model.vo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PointVO {
    // 秘密密钥
    private String secretKey;

    // x坐标
    public int x;

    // y坐标
    public int y;

    // 获取秘密密钥
    public String getSecretKey() {
        return secretKey;
    }

    // 设置秘密密钥
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    // 获取x坐标
    public int getX() {
        return x;
    }

    // 设置x坐标
    public void setX(int x) {
        this.x = x;
    }

    // 获取y坐标
    public int getY() {
        return y;
    }

    // 设置y坐标
    public void setY(int y) {
        this.y = y;
    }

    // 构造函数，传入x、y和secretKey
    public PointVO(int x, int y, String secretKey) {
        this.secretKey = secretKey;
        this.x = x;
        this.y = y;
    }

    // 无参构造函数
    public PointVO() {
    }

    // 构造函数，传入x和y
    public PointVO(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 将PointVO对象转换为json字符串
    public String toJsonString() {
        return String.format("{\"secretKey\":\"%s\",\"x\":%d,\"y\":%d}", secretKey, x, y);
    }

    // 将json字符串转换为PointVO对象
    public PointVO parse(String jsonStr) {
        Map<String, Object> m = new HashMap();
        // 将json字符串中的逗号和花括号替换为空字符串，并将字符串按逗号分割成数组
        Arrays.stream(jsonStr
                .replaceFirst(",\\{", "\\{")
                .replaceFirst("\\{", "")
                .replaceFirst("\\}", "")
                .replaceAll("\"", "")
                .split(",")).forEach(item -> {
            // 将数组中的每个元素按冒号分割，并将分割后的键值对存入Map中
            m.put(item.split(":")[0], item.split(":")[1]);
        });
        //PointVO d = new PointVO();

        setX(Double.valueOf(String.valueOf(m.getOrDefault("x","0"))).intValue());
        setY(Double.valueOf(String.valueOf(m.getOrDefault("y","0"))).intValue());
        setSecretKey(String.valueOf(m.getOrDefault("secretKey", "")));
        // 返回PointVO对象
        return this;
    }

    // 重写equals方法，判断两个PointVO对象是否相等
    @Override
    public boolean equals(Object o) {
        // 如果当前对象与传入的对象是同一个对象，则返回true
        if (this == o) {
            return true;
        }
        // 如果传入的对象为null或者传入的对象的类与当前对象的类不同，则返回false
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PointVO pointVO = (PointVO) o;
        return x == pointVO.x && y == pointVO.y && Objects.equals(secretKey, pointVO.secretKey);
    }

    // 重写hashCode方法，生成PointVO对象的哈希码
    @Override
    public int hashCode() {
        return Objects.hash(secretKey, x, y);
    }
}
