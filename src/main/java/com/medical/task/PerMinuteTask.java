package com.medical.task;

import com.medical.service.BlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 定时任务类
 */
@Slf4j
@Component
public class PerMinuteTask {

    @Resource
    private BlacklistService blacklistService;

    /**
     * 每分钟执行定时任务
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void perMinute() {
        LocalDateTime currentTime = LocalDateTime.now();
        //把黑名单中有解封时间的记录清除
        blacklistService.unlockBlacklist();
    }

}