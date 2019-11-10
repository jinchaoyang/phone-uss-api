package com.renhe.cdr.service;

import com.renhe.cdr.vo.BlackPhone;
import com.renhe.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 数据备份
 */
@Service
public class DBBackupService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    MongoTemplate mongoTemplate;


    private final static String DIC_SOURCE_KEY = "BLACK_PHONE_LIST";

    private final static String DIC_TARGET_KEY = "BLACK_PHONE_LIST_BAK";


    /**
     * 备份
     */
    public boolean backup(){
        boolean result = true;
        String phone = redisTemplate.opsForSet().pop(DIC_SOURCE_KEY);
        if(StringUtil.isPresent(phone)){
            redisTemplate.opsForSet().add(DIC_TARGET_KEY,phone);
            BlackPhone record = new BlackPhone();
            record.setPhoneNo(phone);
            mongoTemplate.save(record);
        }else{
            result = false;
        }
        return result;


    }
}
