package com.aqiu.spzx.manager.service.impl;

import com.aqiu.spzx.common.exception.GuiguException;
import com.aqiu.spzx.manager.mapper.SysMenuMapper;
import com.aqiu.spzx.manager.service.SysMenuService;
import com.aqiu.spzx.manager.utils.MenuHelper;
import com.aqiu.spzx.model.entity.system.SysMenu;
import com.aqiu.spzx.model.entity.system.SysUser;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import com.aqiu.spzx.model.vo.system.SysMenuVo;
import com.aqiu.spzx.utils.AuthContextUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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

    @Override
    public List<SysMenuVo> findMenuByUserId() {
        SysUser sysUser = AuthContextUtil.get();
        Long userId = sysUser.getId();
        List<SysMenu> sysMenuVos = sysMenuMapper.findMenuByUserId(userId);
        List<SysMenu> sysMenus = MenuHelper.buildTree(sysMenuVos);
        return buildMenus(sysMenus);
    }

    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }
}
