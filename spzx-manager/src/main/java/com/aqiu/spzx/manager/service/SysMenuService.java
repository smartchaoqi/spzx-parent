package com.aqiu.spzx.manager.service;

import com.aqiu.spzx.model.entity.system.SysMenu;
import com.aqiu.spzx.model.vo.system.SysMenuVo;

import java.util.List;

public interface SysMenuService {
    List<SysMenu> findNodes();

    void save(SysMenu sysMenu);

    void update(SysMenu sysMenu);

    void removeById(Long id);

    List<SysMenuVo> findMenuByUserId();
}
