package com.aqiu.spzx.common.log.service;

import com.aqiu.spzx.model.entity.system.SysOperLog;

public interface AsyncOperLogService {
    public abstract void saveSysOperLog(SysOperLog sysOperLog) ;
}
