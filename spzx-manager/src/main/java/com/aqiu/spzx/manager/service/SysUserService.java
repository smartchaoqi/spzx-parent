package com.aqiu.spzx.manager.service;

import com.aqiu.spzx.model.dto.system.AssginRoleDto;
import com.aqiu.spzx.model.dto.system.LoginDto;
import com.aqiu.spzx.model.dto.system.SysUserDto;
import com.aqiu.spzx.model.entity.system.SysUser;
import com.aqiu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageInfo;

public interface SysUserService {

    /**
     * 根据用户名查询用户数据
     * @return
     */
    public abstract LoginVo login(LoginDto loginDto) ;

    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteById(Long userId);

    void doAssign(AssginRoleDto assginRoleDto);
}