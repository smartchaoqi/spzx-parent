package com.aqiu.spzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.aqiu.spzx.common.exception.GuiguException;
import com.aqiu.spzx.manager.mapper.SysRoleUserMapper;
import com.aqiu.spzx.manager.mapper.SysUserMapper;
import com.aqiu.spzx.manager.service.SysUserService;
import com.aqiu.spzx.model.dto.system.AssginRoleDto;
import com.aqiu.spzx.model.dto.system.LoginDto;
import com.aqiu.spzx.model.dto.system.SysUserDto;
import com.aqiu.spzx.model.entity.system.SysUser;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import com.aqiu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper ;

    @Autowired
    private RedisTemplate<String , String> redisTemplate ;

    @Override
    public LoginVo login(LoginDto loginDto) {

        String captcha = loginDto.getCaptcha();
        String codeKey = loginDto.getCodeKey();
        String redisCode = redisTemplate.opsForValue().get("user:validate" + codeKey);
        if (StrUtil.isEmpty(redisCode)||!StrUtil.equals(captcha,redisCode)){
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        redisTemplate.delete("user:validate" + codeKey);

        // 根据用户名查询用户
        SysUser sysUser = sysUserMapper.selectByUserName(loginDto.getUserName());
        if(sysUser == null) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 验证密码是否正确
        String inputPassword = loginDto.getPassword();
        String md5InputPassword = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        if(!md5InputPassword.equals(sysUser.getPassword())) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 生成令牌，保存数据到Redis中
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:login:" + token , JSON.toJSONString(sysUser) , 7 , TimeUnit.DAYS);

        // 构建响应结果对象
        LoginVo loginVo = new LoginVo() ;
        loginVo.setToken(token);
        loginVo.setRefresh_token("");

        // 返回
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login:" + token);
        return JSON.parseObject(userJson, SysUser.class);
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login:" + token);
    }

    @Override
    public PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> result = sysUserMapper.findByPage(sysUserDto);
        return PageInfo.of(result);
    }

    @Override
    public void saveSysUser(SysUser sysUser) {
        SysUser selectByUserName = sysUserMapper.selectByUserName(sysUser.getUserName());
        if (selectByUserName!=null){
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        String password = sysUser.getPassword();
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        sysUser.setPassword(md5Password);
        sysUser.setStatus(1);
        sysUserMapper.save(sysUser);
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }

    @Override
    public void deleteById(Long userId) {
        sysUserMapper.delete(userId);
    }

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId());

        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        for (Long aLong : roleIdList) {
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(),aLong);
        }
    }
}