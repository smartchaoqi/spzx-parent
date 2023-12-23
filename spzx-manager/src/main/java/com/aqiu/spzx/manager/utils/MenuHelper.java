package com.aqiu.spzx.manager.utils;

import com.aqiu.spzx.model.entity.system.SysMenu;

import java.util.List;
import java.util.stream.Collectors;

public class MenuHelper {

    public static List<SysMenu> buildTree(List<SysMenu> menus){
        List<SysMenu> sysMenus = buildChildren(menus, 0);
        return sysMenus;
    }

    private static List<SysMenu> buildChildren(List<SysMenu> menus,long parentId){
        List<SysMenu> sysMenuList = menus.stream().filter(menu -> menu.getParentId() == parentId).collect(Collectors.toList());
        sysMenuList.forEach(menu -> {
            menu.setChildren(buildChildren(menus,menu.getId()));
        });
        return sysMenuList;
    }

}
