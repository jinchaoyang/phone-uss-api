package com.renhe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class VerifyService {

    @Autowired
    StringRedisTemplate redisTemplate;

    private static final String BLACK_RECORD =  "BLACK_PHONE_LIST";

    public boolean verify(String phone){
        return redisTemplate.opsForSet().isMember(BLACK_RECORD,phone).booleanValue();
    }
}
