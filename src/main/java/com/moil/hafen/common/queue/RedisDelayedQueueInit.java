package com.moil.hafen.common.queue;

import com.alibaba.fastjson.JSON;
import com.moil.hafen.common.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * redis 延时队列初始化
 *
 * @author SongZichen
 * @since 2023年12月09日 14:26:53
 **/
@Component
@Slf4j
public class RedisDelayedQueueInit implements ApplicationContextAware {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 获取应用上下文并获取相应的接口实现类
     *
     * @param applicationContext
     *
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, RedisDelayedQueueListener> map = applicationContext.getBeansOfType(RedisDelayedQueueListener.class);
        for (Map.Entry<String, RedisDelayedQueueListener> taskEventListenerEntry : map.entrySet()) {
            String listenerName = taskEventListenerEntry.getKey().toLowerCase();
            startThread(listenerName, taskEventListenerEntry.getValue());
        }
    }

    /**
     * 启动线程获取队列
     *
     * @param queueName                 队列名称
     * @param redisDelayedQueueListener 任务回调监听
     */
    private <T> void startThread(String queueName, RedisDelayedQueueListener<T> redisDelayedQueueListener) {
        RBlockingQueue<T> blockingFairQueue = redissonClient.getBlockingQueue(queueName);
        Thread thread = new Thread(() -> {
            LogUtil.info(log, "启动监听队列线程" + queueName);
            while (true) {
                try {
                    T t = blockingFairQueue.take();
                    LogUtil.info(log, "监听队列线程{},获取到值:{}", queueName, JSON.toJSONString(t));
                    redisDelayedQueueListener.invoke(t);
                } catch (Exception e) {
                    LogUtil.error(log, "监听队列线程错误,", e);
                }
            }
        });
        thread.setName(queueName);
        thread.start();
    }
}
