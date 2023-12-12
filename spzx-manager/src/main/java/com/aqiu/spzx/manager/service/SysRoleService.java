package com.aqiu.spzx.manager.service;

import com.aqiu.spzx.model.dto.system.SysRoleDto;
import com.aqiu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface SysRoleService {
    PageInfo<SysRole> findByPage(Integer current, Integer limit, SysRoleDto sysRoleDto);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteById(Long roleId);

    Map<String, Object> findAll(Long userId);
}
