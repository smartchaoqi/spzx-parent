package com.aqiu.spzx.manager.mapper;

import com.aqiu.spzx.model.dto.system.SysRoleDto;
import com.aqiu.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    List<SysRole> findByPage(SysRoleDto sysRoleDto);

    void save(SysRole sysRole);
}
