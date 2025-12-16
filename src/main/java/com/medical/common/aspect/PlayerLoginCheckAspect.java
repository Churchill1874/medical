package com.medical.common.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PlayerLoginCheckAspect {

    //定位切面的目标 是一个注解
    @Pointcut("@annotation(com.medical.common.annotation.PlayerLoginCheck)")
    public void playerLoginCheck() {

    }

    @Before("playerLoginCheck()")
    public void beforeCut(JoinPoint joinPoint) {
/*        PlayerTokenResp playerToken = TokenTools.getPlayerToken(true);
        if (playerToken.getStatus() == null || playerToken.getStatus() == UserStatusEnum.DISABLE) {
            throw new AuthException();
        }*/
    }

/*    @After("loginCheck()")
    public void afterCut(){

    }*/


}
