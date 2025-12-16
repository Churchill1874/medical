package com.medical.common.aspect;

import com.medical.common.exception.IpException;
import com.medical.common.tools.HttpTools;
import com.medical.service.BlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Aspect
@Component
public class BlacklistCheckAspect {

    @Autowired
    private BlacklistService blacklistService;

    //校验controller下面的前台和后台接口
    @Pointcut("execution(* com.medical.controller..*.*(..))")
    public void blacklistPointCut() {
    }

    @Before("blacklistPointCut()")
    public void beforeExecute() {
        String ip = HttpTools.getIp();
        if ("127.0.0.1".equals(ip) || "::1".equals(ip)) {
            return;
        }
        if ("OPTIONS".equalsIgnoreCase(HttpTools.getRequest().getMethod())) {
            return;
        }

        Set<String> blacklistIpSet = blacklistService.getBlacklistIpAll();
        if (blacklistIpSet.contains(ip)) {
            log.error("黑名单ip访问了:{}", ip);
            throw new IpException(ip);
        } else {
            blacklistService.checkIp(ip);
        }
    }

}
