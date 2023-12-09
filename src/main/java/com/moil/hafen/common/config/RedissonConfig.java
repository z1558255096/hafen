package com.moil.hafen.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author SongZichen
 * @since 2023年12月09日 14:28:11
 **/
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer();
        serverConfig.setAddress("redis://r-bp1zn2wllbl5jrlfxepd.redis.rds.aliyuncs.com:6379");
        serverConfig.setPassword("Hafen123!");
        return Redisson.create(config);
    }

}
