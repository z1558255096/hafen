package com.moil.hafen.common.task;

import com.moil.hafen.common.domain.FebsConstant;
import com.moil.hafen.common.service.RedisService;
import com.moil.hafen.common.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 主要用于定时删除 Redis中 key为 febs.user.active 中
 * 已经过期的 score
 */
@Slf4j
@Component
public class CacheTask {

    @Resource
    private RedisService redisService;

    @Scheduled(fixedRate = 3600000)
    public void run() {
        try {
            String now = DateUtil.formatFullTime(LocalDateTime.now());
            redisService.zremrangeByScore(FebsConstant.ACTIVE_USERS_ZSET_PREFIX, "-inf", now);
            log.info("delete expired user");
        } catch (Exception ignore) {
        }
    }
}
