package com.renhe.tenant.service;

import com.renhe.tenant.entity.TenantSetting;
import com.renhe.tenant.mapper.TenantSettingMapper;
import com.renhe.tenant.vo.TenantSettingVo;
import com.renhe.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TenantSettingService {

    @Autowired
    TenantSettingMapper mapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    public int save(TenantSetting setting){
        int result = mapper.save(setting);
        this.clearCache();
        return result;
    }

    public int update(TenantSetting setting){
        int result = mapper.update(setting);
        this.clearCache();
        return result;
    }

    public TenantSetting findById(String tenantId){
        return mapper.findById(tenantId);
    }

    public List<TenantSetting> querByParams(TenantSettingVo vo){
        return mapper.queryByParams(vo);
    }


    public String getTenantByIp(String ip){
        boolean exists = redisTemplate.hasKey(TENANT_IP_KEY);
        if(!exists){
            Map<String,String> ips = getDataFromDB();
            redisTemplate.opsForHash().putAll(TENANT_IP_KEY,ips);
        }
       return redisTemplate.opsForHash().get(TENANT_IP_KEY,ip).toString();
    }



    /**
     * 从数据库中获取数据
     */
    public Map<String,String> getDataFromDB(){
        List<TenantSetting> settings = this.querByParams(new TenantSettingVo());
        Map<String,String> map = new HashMap<>();
        if(null!=settings && !settings.isEmpty()){
            for(TenantSetting setting : settings){
                String ip = setting.getIp();
                String[] arr = ip.split(",");
                for(String str : arr){
                    if(StringUtil.isPresent(str)){
                        map.put(str,setting.getId());
                    }
                }
            }
        }
        return map;
    }

    /**
     * 删除缓存信息
     */
    public void clearCache(){
        redisTemplate.delete(TENANT_IP_KEY);
    }

    private static final String TENANT_IP_KEY="CACHE_TENANT_IP";


}
