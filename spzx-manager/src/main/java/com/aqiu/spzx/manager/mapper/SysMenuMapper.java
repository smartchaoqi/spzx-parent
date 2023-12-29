package com.aqiu.spzx.manager.mapper;

import com.aqiu.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    List<SysMenu> findAll();

    void insert(SysMenu sysMenu);

    void update(SysMenu sysMenu);

    int selectCountByParentId(Long id);

    void deleteById(Long id);

    List<SysMenu> findMenuByUserId(Long userId);
}
