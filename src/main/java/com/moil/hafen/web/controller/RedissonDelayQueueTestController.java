/*
 * 51qbiz.id
 * Copyright (C) 2021-2023 All Rights Reserved.
 */
package com.moil.hafen.web.controller;

import com.moil.hafen.common.queue.RedissonDelayQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * redison延迟队列测试控制器
 *
 * @author zhouyok
 * @version RedissonDelayQueueTestController.java, v 0.1 2023-12-09 0:25 zhouyok
 * @date 2023/12/09
 */
@Slf4j
@RestController
@RequestMapping("redissonDelayQueueTest")
public class RedissonDelayQueueTestController {
    @Resource
    private RedissonDelayQueue redissonDelayQueue;

//    /**
//     * 添加任务
//     *
//     * @param task 任务
//     */
//    @GetMapping("/add")
//    public void addTask(@RequestParam("task") String task) {
//        redissonDelayQueue.offerTask(task, 5);
//    }

}