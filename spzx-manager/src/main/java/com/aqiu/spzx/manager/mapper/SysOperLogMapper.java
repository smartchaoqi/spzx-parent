package com.aqiu.spzx.manager.mapper;

import com.aqiu.spzx.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysOperLogMapper {
    void insert(SysOperLog sysOperLog);
}
