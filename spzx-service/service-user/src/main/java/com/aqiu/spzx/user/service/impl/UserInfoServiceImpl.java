package com.aqiu.spzx.user.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.aqiu.spzx.common.exception.GuiguException;
import com.aqiu.spzx.model.dto.h5.UserRegisterDto;
import com.aqiu.spzx.model.entity.user.UserInfo;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import com.aqiu.spzx.user.mapper.UserInfoMapper;
import com.aqiu.spzx.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public void register(UserRegisterDto userRegisterDto) {
        String code = userRegisterDto.getCode();
        String nickName = userRegisterDto.getNickName();
        String password = userRegisterDto.getPassword();
        String username = userRegisterDto.getUsername();
        if (StringUtils.isBlank(code)||StringUtils.isBlank(nickName)||StringUtils.isBlank(password)||StringUtils.isBlank(username)){
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        String redisCode = redisTemplate.opsForValue().get("phone:code:" + username);
        if(!code.equals(redisCode)){
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        UserInfo userInfo=userInfoMapper.findByUsername(username);
        if(userInfo!=null){
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        userInfoMapper.save(userInfo);
        // 删除Redis中的数据
        redisTemplate.delete("phone:code:" + username) ;
    }
}
