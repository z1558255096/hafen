package com.moil.hafen.common.queue;

import com.moil.hafen.common.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
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

    @PreDestroy
    public void destroy() {
        // 在应用关闭时关闭Redisson客户端
        if (redissonClient != null && !redissonClient.isShutdown()) {
            redissonClient.shutdown();
            LogUtil.info(log, "Redisson客户端已关闭。");
        }
    }

    public <T> void offerTask(T data, Class<? extends RedisDelayedQueueListener<T>> clazz) {
        String queueName = clazz.getSimpleName().toLowerCase();
        LogUtil.info(log, "添加延迟队列,监听名称:{}", queueName);
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
        RDelayedQueue<T> delayedQueue = redissonClient.getDelayedQueue(blockingFairQueue);
        delayedQueue.offerAsync(data, 15, TimeUnit.MINUTES);
    }
}