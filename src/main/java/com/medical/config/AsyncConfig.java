package com.medical.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        // 固定大小的线程池
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);    // 核心线程数
        executor.setMaxPoolSize(8);     // 最大线程数
        executor.setQueueCapacity(200); // 队列容量
        executor.setThreadNamePrefix("AsyncDefault-");
        executor.initialize();
        return executor;
    }
}
