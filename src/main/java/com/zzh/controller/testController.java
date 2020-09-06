package com.zzh.controller;

import com.zzh.service.testRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class testController {

    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    testRedis testRedis;
    @ResponseBody
    @RequestMapping("/a")
    public String a() {
    testRedis.pr();
    return "a";
    }

}
