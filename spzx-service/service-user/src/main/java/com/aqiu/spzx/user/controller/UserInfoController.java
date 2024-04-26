package com.aqiu.spzx.user.controller;

import com.aqiu.spzx.model.dto.h5.UserLoginDto;
import com.aqiu.spzx.model.dto.h5.UserRegisterDto;
import com.aqiu.spzx.model.vo.common.Result;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import com.aqiu.spzx.model.vo.h5.UserInfoVo;
import com.aqiu.spzx.user.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto){
        userInfoService.register(userRegisterDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("login")
    public Result login(@RequestBody UserLoginDto userLoginDto) {
        String token = userInfoService.login(userLoginDto);
        return Result.build(token, ResultCodeEnum.SUCCESS);
    }

    @GetMapping("auth/getCurrentUserInfo")
    public Result getCurrentUserInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserInfoVo userInfoVo = userInfoService.getUserInfoByToken(token);
        return Result.build(userInfoVo, ResultCodeEnum.SUCCESS);
    }
}
