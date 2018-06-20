package com.example.demo.controller;

import com.example.demo.repository.RedisCommDAO;
import com.example.demo.type.REDIS_PUB_KEYS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RedisCommDAO redisCommDAO;

    @RequestMapping(method = RequestMethod.GET, value="/test")
    public void pubsub() {
        redisCommDAO.convertAndSend(REDIS_PUB_KEYS.UI_TRADE_PUB.getKey(), "test");
    }

}
