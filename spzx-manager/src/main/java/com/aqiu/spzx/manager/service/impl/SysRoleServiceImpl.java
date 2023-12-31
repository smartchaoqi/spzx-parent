package com.aqiu.spzx.manager.service.impl;

import com.aqiu.spzx.manager.mapper.SysRoleMapper;
import com.aqiu.spzx.manager.mapper.SysRoleUserMapper;
import com.aqiu.spzx.manager.service.SysRoleService;
import com.aqiu.spzx.model.dto.system.SysRoleDto;
import com.aqiu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public PageInfo<SysRole> findByPage(Integer current, Integer limit, SysRoleDto sysRoleDto) {
        PageHelper.startPage(current, limit);
        List<SysRole> list = sysRoleMapper.findByPage(sysRoleDto);
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.save(sysRole);
    }

    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
    }

    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.delete(roleId);
    }

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Override
    public Map<String, Object> findAll(Long userId) {
        Map<String,Object> map=new HashMap<>();
        List<SysRole> sysRoles = sysRoleMapper.findAll();
        List<Long> roleIds = sysRoleUserMapper.selectRoleIdsByUserId(userId);
        map.put("allRolesList",sysRoles);
        map.put("sysUserRoles",roleIds);
        return map;
    }
}
