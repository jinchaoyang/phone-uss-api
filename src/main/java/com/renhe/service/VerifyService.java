package com.renhe.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.renhe.cdr.vo.BlackRecordVo;
import com.renhe.tenant.service.TenantSettingService;
import com.renhe.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;
import java.util.Set;

@Service
public class VerifyService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    MongoTemplate mongoTemplate;



    private static final String BLACK_RECORD =  "BLACK_PHONE_LIST";

    private static final String STAT_PREFIX="M_STAT_";

    private static final String STAT_BLACK_PREFIX="M_STAT_BLACK_";


    private static final String VIP_RECORD =  "VIP_PHONE_LIST";

    private static final String STAT_VIP_PREFIX="M_STAT_VIP_";

    public boolean verify(String phone){
        return redisTemplate.opsForSet().isMember(BLACK_RECORD,phone).booleanValue();
    }

    public void count(String ip,String key){
        redisTemplate.opsForHash().increment(STAT_PREFIX+ip,key,1l);
    }

    public void countBlack(String ip,String key){
        redisTemplate.opsForHash().increment(STAT_BLACK_PREFIX+ip,key,1l);
    }

    public JSONArray getAllByDicName(String ip){
        JSONArray array = new JSONArray();
        Map<Object,Object> counter =  redisTemplate.opsForHash().entries(STAT_PREFIX+ip);
        Map<Object,Object> blackCounter =  redisTemplate.opsForHash().entries(STAT_BLACK_PREFIX+ip);
        Set<Map.Entry<Object,Object>> entrySet = counter.entrySet();
        for(Map.Entry<Object,Object> entry : entrySet){
            Object key = entry.getKey();
            Object value = entry.getValue();
            JSONObject obj = new JSONObject();
            obj.put("date",key);
            obj.put("total",value);
            obj.put("forbid",blackCounter.getOrDefault(key,"0"));
            array.add(obj);
        }
        return array;
    }


    public long uploadBlackRecord(File targetFile) throws IOException {
        BufferedReader in  = new BufferedReader(new FileReader(targetFile));
        String line = null;
        long result = 0l;
        while((line = in.readLine())!=null){
            if(StringUtil.isPresent(line)){
                line = StringUtil.trim(line);
                result = redisTemplate.opsForSet().add(BLACK_RECORD,line);
            }
        }
        return result;

    }


    public boolean verifyVip(String phone){
        return redisTemplate.opsForSet().isMember(VIP_RECORD,phone).booleanValue();
    }


    public void countVip(String ip,String key){
        redisTemplate.opsForHash().increment(STAT_VIP_PREFIX+ip,key,1l);
    }


    public long uploadVipRecord(File targetFile) throws IOException {
        BufferedReader in  = new BufferedReader(new FileReader(targetFile));
        String line = null;
        long result = 0l;
        while((line = in.readLine())!=null){
            if(StringUtil.isPresent(line)){
                line = StringUtil.trim(line);
                result = redisTemplate.opsForSet().add(VIP_RECORD,line);
            }
        }
        return result;

    }


    public void saveBlackRecord(BlackRecordVo vo){
        mongoTemplate.save(vo);
    }




}
