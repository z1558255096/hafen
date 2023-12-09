package com.moil.hafen.common.queue;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouyok
 * @version RedissonDelayQueue.java, v 0.1 2023-12-09 0:15 zhouyok
 */

@Component
@Slf4j
public class RedissonDelayQueue {

    @Resource
    private RedissonClient redissonClient;

    private RDelayedQueue<String> delayQueue;
    private RBlockingQueue<String> blockingQueue;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer();
        serverConfig.setAddress("redis://r-bp1zn2wllbl5jrlfxepd.redis.rds.aliyuncs.com:6379");
        serverConfig.setPassword("Hafen123!");
        return Redisson.create(config);
    }

    @PostConstruct
    public void init() {
        initDelayQueue();
        startDelayQueueConsumer();
    }

    private void initDelayQueue() {
        blockingQueue = redissonClient.getBlockingQueue("HAFEN");
        delayQueue = redissonClient.getDelayedQueue(blockingQueue);
    }

    private void startDelayQueueConsumer() {
        new Thread(() -> {
            while (true) {
                try {
                    String task = blockingQueue.take();
                    log.info("接收到延迟任务:{} ", task);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "HAFEN-Consumer").start();
    }

    public void offerTask(String task, long seconds) {
        log.info("添加延迟任务:{} 延迟时间:{}s", task, seconds);
        delayQueue.offer(task, seconds, TimeUnit.SECONDS);
    }
}