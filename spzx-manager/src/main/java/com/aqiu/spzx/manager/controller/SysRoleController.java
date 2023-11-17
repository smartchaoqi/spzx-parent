package com.aqiu.spzx.manager.controller;

import com.aqiu.spzx.manager.service.SysRoleService;
import com.aqiu.spzx.model.dto.system.SysRoleDto;
import com.aqiu.spzx.model.entity.system.SysRole;
import com.aqiu.spzx.model.vo.common.Result;
import com.aqiu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.simpleframework.xml.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("/findByPage/{current}/{limit}")
    public Result findByPage(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             @RequestBody SysRoleDto sysRoleDto) {
        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(current, limit, sysRoleDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping(value = "/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole SysRole) {
        sysRoleService.saveSysRole(SysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

}
