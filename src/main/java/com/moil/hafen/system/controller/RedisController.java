package com.moil.hafen.system.controller;

import com.moil.hafen.common.domain.FebsResponse;
import com.moil.hafen.common.domain.RedisInfo;
import com.moil.hafen.common.service.RedisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("redis")
public class RedisController {

    @Resource
    private RedisService redisService;

    @GetMapping("info")
    public FebsResponse getRedisInfo() throws Exception {
        List<RedisInfo> infoList = this.redisService.getRedisInfo();
        return new FebsResponse().data(infoList);
    }

    @GetMapping("keysSize")
    public Map<String, Object> getKeysSize() throws Exception {
        return redisService.getKeysSize();
    }

    @GetMapping("memoryInfo")
    public Map<String, Object> getMemoryInfo() throws Exception {
        return redisService.getMemoryInfo();
    }
}
