package com.example.demo.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration {

	@Bean
    public Executor asyncThreadPool() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(3); //동시에 실행시킬 쓰레드의 개수
        taskExecutor.setMaxPoolSize(30); //쓰레드 풀의 최대 사이즈
        taskExecutor.setQueueCapacity(10); //쓰레드 풀 큐의 사이즈
        taskExecutor.setThreadNamePrefix("Async-Executor-"); //쓰레드명
        taskExecutor.setDaemon(true);//데몬쓰레드
        taskExecutor.initialize();

        return taskExecutor;
    }
}
