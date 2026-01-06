package com.medical.common.aspect;

import com.medical.common.exception.AuthException;
import com.medical.common.tools.TokenTools;
import com.medical.entity.Admin;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AdminLoginCheckAspect {

    //定位切面的目标 是一个注解
    @Pointcut("@annotation(com.medical.common.annotation.AdminLoginCheck)")
    public void adminLoginCheck() {

    }

    @Before("adminLoginCheck()")
    public void beforeCut(JoinPoint joinPoint) {
        Admin admin = TokenTools.getAdminToken(true);
        if (!admin.getStatus()) {
            throw new AuthException();
        }
    }

/*    @After("loginCheck()")
    public void afterCut(){

    }*/


}
