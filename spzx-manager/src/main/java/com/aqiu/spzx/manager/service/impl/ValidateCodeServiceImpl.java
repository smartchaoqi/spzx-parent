package com.aqiu.spzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.aqiu.spzx.manager.service.ValidateCodeService;
import com.aqiu.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisTemplate<String , String> redisTemplate ;

    @Override
    public ValidateCodeVo generateValidateCode() {
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 5);
        ValidateCodeVo vo = new ValidateCodeVo();
        String code = circleCaptcha.getCode();
        String imageBase64 = circleCaptcha.getImageBase64();

        String key = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:validate"+key,code,5, TimeUnit.MINUTES);

        vo.setCodeValue("data:image/png;base64,"+imageBase64);
        vo.setCodeKey(key);
        return vo;
    }
}
