package com.aqiu.spzx.user.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.aqiu.spzx.common.exception.GuiguException;
import com.aqiu.spzx.model.dto.h5.UserLoginDto;
import com.aqiu.spzx.model.dto.h5.UserRegisterDto;
import com.aqiu.spzx.model.entity.user.UserInfo;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import com.aqiu.spzx.model.vo.h5.UserInfoVo;
import com.aqiu.spzx.user.mapper.UserInfoMapper;
import com.aqiu.spzx.user.service.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

    @Override
    public String login(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        if (StringUtils.isBlank(username)||StringUtils.isBlank(password)){
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        UserInfo userInfo=userInfoMapper.findByUsername(username);
        if(userInfo==null){
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        if(!userInfo.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        if (userInfo.getStatus()==0){
            throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
        }
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:spzx:" + token, JSON.toJSONString(userInfo), 30, TimeUnit.DAYS);
        return token;
    }

    @Override
    public UserInfoVo getUserInfoByToken(String token) {
        String userInfoStr = redisTemplate.opsForValue().get("user:spzx:" + token);
        if(StringUtils.isBlank(userInfoStr)){
            throw new GuiguException(ResultCodeEnum.LOGIN_AUTH) ;
        }
        UserInfo userInfo = JSON.parseObject(userInfoStr, UserInfo.class);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return userInfoVo;
    }
}
