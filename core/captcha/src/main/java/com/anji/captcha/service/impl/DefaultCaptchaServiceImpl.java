
package com.anji.captcha.service.impl;

import com.anji.captcha.model.common.RepCodeEnum;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.anji.captcha.util.StringUtils;

import java.util.Map;
import java.util.Properties;

/**
 * 默认验证码实现
 */
public class DefaultCaptchaServiceImpl extends AbstractCaptchaService{

    @Override
    public String captchaType() {
        return "default";
    }

    @Override
    public void init(Properties config) {
        //遍历CaptchaServiceFactory.instances，如果当前实例的类型与captchaType()相同，则跳过，否则调用entry.getValue().init(config)进行初始化
        for (Map.Entry<String, CaptchaService> entry : CaptchaServiceFactory.instances.entrySet()) {
            if(captchaType().equals(entry.getKey())){
                continue;
            }
            entry.getValue().init(config);
        }
    }

	@Override
	public void destroy(Properties config) {
        //遍历CaptchaServiceFactory.instances，如果当前实例的类型与captchaType()相同，则跳过，否则调用entry.getValue().destroy(config)进行销毁
        for (Map.Entry<String, CaptchaService> entry : CaptchaServiceFactory.instances.entrySet()) {
            if(captchaType().equals(entry.getKey())){
                continue;
            }
            entry.getValue().destroy(config);
        }
	}

	private CaptchaService getService(String captchaType){
        //根据captchaType从CaptchaServiceFactory.instances中获取对应的CaptchaService实例
        return CaptchaServiceFactory.instances.get(captchaType);
    }

    @Override
    public ResponseModel get(CaptchaVO captchaVO) {
        //如果captchaVO为空，则返回NULL_ERROR错误
        if (captchaVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("captchaVO");
        }
        //如果captchaVO.getCaptchaType()为空，则返回NULL_ERROR错误
        if (StringUtils.isEmpty(captchaVO.getCaptchaType())) {
            return RepCodeEnum.NULL_ERROR.parseError("类型");
        }
        //调用getService(captchaVO.getCaptchaType()).get(captchaVO)获取验证码
        return getService(captchaVO.getCaptchaType()).get(captchaVO);
    }

    @Override
    public ResponseModel check(CaptchaVO captchaVO) {
        //如果captchaVO为空，则返回NULL_ERROR错误
        if (captchaVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("captchaVO");
        }
        //如果captchaVO.getCaptchaType()为空，则返回NULL_ERROR错误
        if (StringUtils.isEmpty(captchaVO.getCaptchaType())) {
            return RepCodeEnum.NULL_ERROR.parseError("类型");
        }
        //如果captchaVO.getToken()为空，则返回NULL_ERROR错误
        if (StringUtils.isEmpty(captchaVO.getToken())) {
            return RepCodeEnum.NULL_ERROR.parseError("token");
        }
        //调用getService(captchaVO.getCaptchaType()).check(captchaVO)进行验证
        return getService(captchaVO.getCaptchaType()).check(captchaVO);
    }

    @Override
    public ResponseModel verification(CaptchaVO captchaVO) {
        //如果captchaVO为空，则返回NULL_ERROR错误
        if (captchaVO == null) {
            return RepCodeEnum.NULL_ERROR.parseError("captchaVO");
        }
        //如果captchaVO.getCaptchaVerification()为空，则返回NULL_ERROR错误
        if (StringUtils.isEmpty(captchaVO.getCaptchaVerification())) {
            return RepCodeEnum.NULL_ERROR.parseError("二次校验参数");
        }
        try {
            //根据captchaVO.getCaptchaVerification()生成codeKey
            String codeKey = String.format(REDIS_SECOND_CAPTCHA_KEY, captchaVO.getCaptchaVerification());
            //如果缓存中不存在codeKey，则返回API_CAPTCHA_INVALID错误
            if (!CaptchaServiceFactory.getCache(cacheType).exists(codeKey)) {
                return ResponseModel.errorMsg(RepCodeEnum.API_CAPTCHA_INVALID);
            }
            //二次校验取值后，即刻失效
            CaptchaServiceFactory.getCache(cacheType).delete(codeKey);
        } catch (Exception e) {
            logger.error("验证码坐标解析失败", e);
            return ResponseModel.errorMsg(e.getMessage());
        }
        //返回成功
        return ResponseModel.success();
    }

}
