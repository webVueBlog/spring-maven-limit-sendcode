package com.anji.captcha.model.common;

/**
 * 底图类型枚举
 */
public enum CaptchaBaseMapEnum {
    ROTATE("ROTATE", "旋转拼图底图"),
    ROTATE_BLOCK("ROTATE_BLOCK", "旋转拼图旋转块底图"),
    ORIGINAL("ORIGINAL", "滑动拼图底图"),
    SLIDING_BLOCK("SLIDING_BLOCK", "滑动拼图滑块底图"),
    PIC_CLICK("PIC_CLICK", "文字点选底图");

    // 代码值
    private String codeValue;
    // 代码描述
    private String codeDesc;

    private CaptchaBaseMapEnum(String codeValue, String codeDesc) {
        this.codeValue = codeValue;
        this.codeDesc = codeDesc;
    }

    public String getCodeValue() {
        return this.codeValue;
    }

    public String getCodeDesc() {
        return this.codeDesc;
    }

    //根据codeValue获取枚举
    public static CaptchaBaseMapEnum parseFromCodeValue(String codeValue) {
        for (CaptchaBaseMapEnum e : CaptchaBaseMapEnum.values()) {
            if (e.codeValue.equals(codeValue)) {
                return e;//返回枚举值 : ROTATE
            }
        }
        return null;
    }

    //根据codeValue获取描述
    public static String getCodeDescByCodeBalue(String codeValue) {
        CaptchaBaseMapEnum enumItem = parseFromCodeValue(codeValue);
        return enumItem == null ? "" : enumItem.getCodeDesc();//返回值 : "旋转拼图底图"
    }

    //验证codeValue是否有效
    public static boolean validateCodeValue(String codeValue) {
        return parseFromCodeValue(codeValue) != null;
    }

    //列出所有值字符串
    // 返回一个字符串，包含CaptchaBaseMapEnum枚举类中所有枚举值的codeValue和codeDesc
    public static String getString() {
        StringBuffer buffer = new StringBuffer();
        for (CaptchaBaseMapEnum e : CaptchaBaseMapEnum.values()) {
            // 将枚举值的codeValue和codeDesc添加到buffer中，并用"--"分隔
            buffer.append(e.codeValue).append("--").append(e.getCodeDesc()).append(", ");
        }
        // 删除buffer中最后一个逗号
        buffer.deleteCharAt(buffer.lastIndexOf(","));
        // 返回buffer中的字符串
        return buffer.toString().trim();
    }

}
