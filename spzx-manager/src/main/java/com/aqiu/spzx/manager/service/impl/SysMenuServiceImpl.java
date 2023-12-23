package com.aqiu.spzx.manager.service.impl;

import com.aqiu.spzx.common.exception.GuiguException;
import com.aqiu.spzx.manager.mapper.SysMenuMapper;
import com.aqiu.spzx.manager.service.SysMenuService;
import com.aqiu.spzx.manager.utils.MenuHelper;
import com.aqiu.spzx.model.entity.system.SysMenu;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    public List<SysMenu> findNodes() {
        List<SysMenu> sysMenus = sysMenuMapper.findAll();
        if (sysMenus==null){
            return null;
        }else{
            return MenuHelper.buildTree(sysMenus);
        }
    }

    @Override
    public void save(SysMenu sysMenu) {
        sysMenuMapper.insert(sysMenu);
    }

    @Override
    public void update(SysMenu sysMenu) {
        sysMenuMapper.update(sysMenu);
    }

    @Override
    public void removeById(Long id) {
        int count = sysMenuMapper.selectCountByParentId(id);
        if (count>0){
            throw new GuiguException(ResultCodeEnum.NODE_ERROR);
        }
        sysMenuMapper.deleteById(id);
    }
}
