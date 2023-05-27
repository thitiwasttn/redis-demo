package com.thitiwas.demo.redisdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Configuration
@Slf4j
public class RedisConfig {
    private final RedisProperties redisProperties;

    @Autowired
    public RedisConfig(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
        clearAllCache();
    }

    private void clearAllCache() {
        log.info("redisHost :{}", redisProperties.getHost());
        log.info("redisPort :{}", redisProperties.getPort());
        JedisPool pool = new JedisPool(redisProperties.getHost(), redisProperties.getPort());
        try {
            Jedis resource = pool.getResource();
            resource.flushAll();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("clear all cache error ", e);
        }
        log.info("cleared all cache");
    }
}
