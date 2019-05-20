package com.renhe.callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.Callable;

/**
 * 黑名单导入到elastichsearch
 */
public class ImportTask implements Callable {

    private static final Logger logger = LoggerFactory.getLogger(ImportTask.class);

    private StringRedisTemplate redisTemplate;

    private static final String BLACK_RECORD =  "BLACK_PHONE_LIST";

    public ImportTask(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Object call() throws Exception {
       long size =  redisTemplate.opsForSet().size(BLACK_RECORD);
       logger.info("black record size -->"+size);
        return null;
    }


    public StringRedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
