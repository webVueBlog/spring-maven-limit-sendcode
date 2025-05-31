
package com.anji.captcha.controller;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    // 获取验证码
    @PostMapping("/get")
    public ResponseModel get(@RequestBody CaptchaVO data, HttpServletRequest request) {
        // 断言请求的远程主机不为空
        assert request.getRemoteHost()!=null;
        // 设置浏览器信息
        data.setBrowserInfo(getRemoteId(request));
        return captchaService.get(data);
    }

    // 验证验证码
    @PostMapping("/check")
    public ResponseModel check(@RequestBody CaptchaVO data, HttpServletRequest request) {
        // 设置浏览器信息
        data.setBrowserInfo(getRemoteId(request));
        return captchaService.check(data);
    }

    //@PostMapping("/verify")
    // 验证验证码
    public ResponseModel verify(@RequestBody CaptchaVO data, HttpServletRequest request) {
        return captchaService.verification(data);
    }

    // 获取远程IP地址
    public static final String getRemoteId(HttpServletRequest request) {
        String xfwd = request.getHeader("X-Forwarded-For");
        String ip = getRemoteIpFromXfwd(xfwd);

        String ua = request.getHeader("user-agent");
        if (StringUtils.isNotBlank(ip)) {
            return ip + ua;
        }

        return request.getRemoteAddr() + ua;
    }

    private static String getRemoteIpFromXfwd(String xfwd) {
        // 如果X-Forwarded-For头部信息不为空，则返回第一个IP地址
        if (StringUtils.isNotBlank(xfwd)) {
            String[] ipList = xfwd.split(",");
            return StringUtils.trim(ipList[0]);
        }
        return null;
    }

}
