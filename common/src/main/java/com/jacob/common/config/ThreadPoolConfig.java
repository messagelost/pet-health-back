package com.jacob.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ThreadPoolConfig {

    @Bean("notifyExecutor")
    public Executor notifyExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(10);        // 核心线程
        executor.setMaxPoolSize(50);         // 最大线程
        executor.setQueueCapacity(1000);     // 队列
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("notify-");

        executor.initialize();

        return executor;
    }

    @Bean("userExecutor")
    public Executor userExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(10);        // 核心线程
        executor.setMaxPoolSize(50);         // 最大线程
        executor.setQueueCapacity(1000);     // 队列
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("user-");

        executor.initialize();

        return executor;
    }
}
