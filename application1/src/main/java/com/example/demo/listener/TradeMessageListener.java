package com.example.demo.listener;

import com.example.demo.repository.RedisCommDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;

public class TradeMessageListener implements MessageListener {

	Logger L = LoggerFactory.getLogger(getClass());

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
    private RedisCommDAO redisCommDAO;

	@Override
	public void onMessage(Message message, byte[] bytes) {

		L.info("===========================");
		L.info(message.toString());
		byte[] body = message.getBody();
		String json = redisTemplate.getStringSerializer().deserialize(body);

	}

}
