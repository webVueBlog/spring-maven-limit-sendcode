package com.anji.captcha.demo.controller;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后端二次校验接口示例
 */
@RestController//标识该类为控制器
@RequestMapping("/auth")//标识该类下所有接口的公共路径
public class LoginController {

    @Autowired
    private CaptchaService captchaService;

    @PostMapping("/login")
    public ResponseModel get(@RequestParam("captchaVerification") String captchaVerification) {
        //用于存储验证码信息
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(captchaVerification);
        //进行验证码校验
        ResponseModel response = captchaService.verification(captchaVO);
        //如果验证码校验失败
        if(response.isSuccess() == false){
            //验证码校验失败，返回信息告诉前端
            //repCode  0000  无异常，代表成功
            //repCode  9999  服务器内部异常
            //repCode  0011  参数不能为空
            //repCode  6110  验证码已失效，请重新获取
            //repCode  6111  验证失败
            //repCode  6112  获取验证码失败,请联系管理员
        }
        return response;
    }
}
