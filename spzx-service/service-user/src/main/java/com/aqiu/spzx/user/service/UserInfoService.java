package com.aqiu.spzx.user.service;

import com.aqiu.spzx.model.dto.h5.UserLoginDto;
import com.aqiu.spzx.model.dto.h5.UserRegisterDto;
import com.aqiu.spzx.model.vo.h5.UserInfoVo;

public interface UserInfoService {
    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo getUserInfoByToken(String token);
}
