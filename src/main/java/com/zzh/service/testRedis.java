package com.zzh.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 张子行
 * @class redis实现延时消费
 */
@Component
public class testRedis {
    @Autowired
    RedisTemplate redisTemplate;
    String QUEUENAME = "zzh:queue";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Test
    public void pr() {

//        long creatTime = System.currentTimeMillis();
//        long c = creatTime + 8 * 30;
//        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c);

        for (int i = 1; i <= 1; i++) {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + i);
            System.out.println("生产了：" + i + "当前时间为：" + simpleDateFormat.format(System.currentTimeMillis()) + "消费时间为：" + simpleDateFormat.format(now.getTime()));
            //往QUEUENAME这个集合中放入i，设置scorce为排序规则
            redisTemplate.opsForZSet().add(QUEUENAME, i, now.getTime().getTime());
        }

    }

    @Scheduled(cron = "*/1 * * * * * ")
    public void cs() {
        System.out.println("------------等待消费--------------");
        //取出QUEUENAME集合中的score在0-当前时间戳这个范围的所有值
        Set<Integer> set = redisTemplate.opsForZSet().rangeByScore(QUEUENAME, 0, System.currentTimeMillis());
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            //遍历取出每一个score
            Double score = redisTemplate.opsForZSet().score(QUEUENAME, value);
            //达到了时间就进行消费
            if (System.currentTimeMillis() > score) {
                System.out.println("消费了：" + value + "消费时间为：" + simpleDateFormat.format(System.currentTimeMillis()));
                redisTemplate.opsForZSet().remove(QUEUENAME, value);

            }
        }


    }
}
