package com.aqiu.spzx.manager.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.aqiu.spzx.manager.service.SysMenuService;
import com.aqiu.spzx.manager.service.SysUserService;
import com.aqiu.spzx.manager.service.ValidateCodeService;
import com.aqiu.spzx.model.dto.system.LoginDto;
import com.aqiu.spzx.model.entity.system.SysUser;
import com.aqiu.spzx.model.vo.common.Result;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import com.aqiu.spzx.model.vo.system.LoginVo;
import com.aqiu.spzx.model.vo.system.SysMenuVo;
import com.aqiu.spzx.model.vo.system.ValidateCodeVo;
import com.aqiu.spzx.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService ;

    @Autowired
    private ValidateCodeService validateCodeService;

    @Operation(summary = "登录接口")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto) ;
        return Result.build(loginVo , ResultCodeEnum.SUCCESS) ;
    }

    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo vo = validateCodeService.generateValidateCode();
        return Result.build(vo , ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestHeader("token") String token) {
        SysUser sysUser = AuthContextUtil.get();
//        SysUser user = sysUserService.getUserInfo(token);
        return Result.build(sysUser , ResultCodeEnum.SUCCESS) ;
    }

    @GetMapping("/logout")
    public Result logout(@RequestHeader("token") String token) {
        sysUserService.logout(token);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/menus")
    public Result menus() {
        List<SysMenuVo> res = sysMenuService.findMenuByUserId();
        return Result.build(res , ResultCodeEnum.SUCCESS) ;
    }

}