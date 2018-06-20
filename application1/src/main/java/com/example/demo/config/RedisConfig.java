package com.example.demo.config;

import com.example.demo.listener.ResetTradeMessageListener;
import com.example.demo.listener.TradeMessageListener;
import com.example.demo.listener.WithdrawMessageListener;
import com.example.demo.type.REDIS_PUB_KEYS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableTransactionManagement
public class RedisConfig {

    private Logger L = LoggerFactory.getLogger(this.getClass());

    @Resource
    private Environment environment;

    private final String REDIS_MASTER = "redis.master";

    private final String REDIS_PW = "redis.pwd";

    private final String SENTINEL_M1 = "sentinel.monitor1";

    private final String SENTINEL_M2 = "sentinel.monitor2";

    private final String SENTINEL_M3 = "sentinel.monitor3";

    private final int REDIS_TIMEOUT = 0;

    private String getProp(String key){
        return environment.getRequiredProperty(key);
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(128);
        return jedisPoolConfig;
    }

    @Bean
    public JedisSentinelPool redisSentinelPool(JedisPoolConfig jedisPoolConfig){
        Set<String> sentinelSet = new HashSet<>();
        sentinelSet.add(getProp(SENTINEL_M1));
//        sentinelSet.add(getProp(SENTINEL_M2));
//        sentinelSet.add(getProp(SENTINEL_M3));

        return new JedisSentinelPool(
                getProp(REDIS_MASTER)
                , sentinelSet
                , jedisPoolConfig
                , REDIS_TIMEOUT
                , getProp(REDIS_PW)
        );
    }

    @Bean
    public GenericToStringSerializer genericToStringSerializer() {
        return new GenericToStringSerializer(Object.class);
    }

    @Bean
    public RedisSentinelConnectionFactory jedisConnectionFactory(JedisSentinelPool redisSentinelPool){
        RedisSentinelConnectionFactory jedisConnectionFactory = new RedisSentinelConnectionFactory();
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setPassword(REDIS_PW);
        jedisConnectionFactory.setSentinelPool(redisSentinelPool);
        return jedisConnectionFactory;
    }

    @Bean
    public StringRedisTemplate redisTemplate(RedisSentinelConnectionFactory jedisConnectionFactory, GenericToStringSerializer genericToStringSerializer){
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setValueSerializer(genericToStringSerializer);
        return redisTemplate;
    }

    @Bean
    public TradeMessageListener tradeMessageListener(){
        return new TradeMessageListener();
    }

    @Bean
    public WithdrawMessageListener withdrawMessageListener(){
        return new WithdrawMessageListener();
    }

    @Bean
    public ResetTradeMessageListener resetTradeMessageListener(){
        return new ResetTradeMessageListener();
    }

    @Bean
    public RedisMessageListenerContainer container(RedisSentinelConnectionFactory connectionFactory) {
    	RedisMessageListenerContainer container = new RedisMessageListenerContainer();
    	container.setConnectionFactory(connectionFactory);
    	container.addMessageListener(tradeMessageListener(), new ChannelTopic(REDIS_PUB_KEYS.UI_TRADE_PUB.getKey()));
    	container.addMessageListener(withdrawMessageListener(), new ChannelTopic(REDIS_PUB_KEYS.UI_WITHDRAW_PUB.getKey()));
    	container.addMessageListener(resetTradeMessageListener(), new ChannelTopic(REDIS_PUB_KEYS.UI_REST.getKey()));
    	return container;
	}
}
