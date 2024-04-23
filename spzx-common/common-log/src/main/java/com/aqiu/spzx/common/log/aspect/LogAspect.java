package com.aqiu.spzx.common.log.aspect;

import com.aqiu.spzx.common.log.annotation.Log;
import com.aqiu.spzx.common.log.service.AsyncOperLogService;
import com.aqiu.spzx.common.log.utils.LogUtil;
import com.aqiu.spzx.model.entity.system.SysOperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {
    @Autowired
    private AsyncOperLogService asyncOperLogService ;

    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint , Log sysLog) {
        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.beforeHandleLog(sysLog,joinPoint,sysOperLog);
        Object proceed=null;
        try {
            proceed = joinPoint.proceed();
            LogUtil.afterHandleLog(sysLog,proceed,sysOperLog,0,null);
        } catch (Throwable e) {
            LogUtil.afterHandleLog(sysLog,proceed,sysOperLog,1,e.getMessage());
            throw new RuntimeException(e);
        }
        // 保存日志数据
        asyncOperLogService.saveSysOperLog(sysOperLog);
        return proceed;
    }
}
