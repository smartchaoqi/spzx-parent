package com.aqiu.spzx.manager.service.impl;

import com.aqiu.spzx.manager.mapper.SysRoleMenuMapper;
import com.aqiu.spzx.manager.service.SysMenuService;
import com.aqiu.spzx.manager.service.SysRoleMenuService;
import com.aqiu.spzx.model.dto.system.AssginMenuDto;
import com.aqiu.spzx.model.entity.system.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
        List<SysMenu> nodes = sysMenuService.findNodes();

        List<Long> roleMenuIds = sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);

        Map<String,Object> result=new HashMap<>();
        result.put("sysMenuList",nodes);
        result.put("roleMenuIds",roleMenuIds);
        return result;
    }

    @Override
    public void deleteByRoleId(Long roleId) {

    }

    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {
        sysRoleMenuMapper.deleteByRoleId(assginMenuDto.getRoleId());
        List<Map<String, Number>> menuIdList = assginMenuDto.getMenuIdList();
        if (menuIdList!=null && menuIdList.size()>0){
            sysRoleMenuMapper.doAssign(assginMenuDto);
        }
    }
}
