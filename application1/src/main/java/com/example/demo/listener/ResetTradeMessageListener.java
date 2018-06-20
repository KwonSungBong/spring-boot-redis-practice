package com.example.demo.listener;

import com.example.demo.repository.RedisCommDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;

public class ResetTradeMessageListener  implements MessageListener {
	Logger L = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private RedisCommDAO redisCommDAO;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Override
	public void onMessage(Message message, byte[] pattern) {
		byte[] body = message.getBody();
		String rtJson = redisTemplate.getStringSerializer().deserialize(body);
		L.info(rtJson);

	}

}
