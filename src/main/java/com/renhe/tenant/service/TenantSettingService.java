package com.renhe.tenant.service;

import com.renhe.tenant.entity.TenantSetting;
import com.renhe.tenant.mapper.TenantSettingMapper;
import com.renhe.tenant.vo.TenantSettingVo;
import com.renhe.utils.Constant;
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

    @Autowired
    TenantProductService tenantProductService;

    public int save(TenantSetting setting){
        int result = mapper.save(setting);
        if(result>0){
            boolean valid = tenantProductService.blackValid(setting.getId());
            if(valid && StringUtil.isPresent(setting.getIp())){
                redisTemplate.opsForSet().add(Constant.CHECKER.IP_LIST_NAME,setting.getIp());
            }
        }
        return result;
    }

    public int update(TenantSetting setting){
        TenantSetting _tmp = this.findById(setting.getId());
        int result = mapper.update(setting);
        if(result>0){
            if(!StringUtil.trim(_tmp.getIp()).equals(StringUtil.trim(setting.getIp()))){
                redisTemplate.opsForSet().remove(Constant.CHECKER.IP_LIST_NAME,_tmp.getIp());
            }
            boolean valid = tenantProductService.blackValid(setting.getId());
            if(valid){
                redisTemplate.opsForSet().add(Constant.CHECKER.IP_LIST_NAME,StringUtil.trim(setting.getIp()));
            }else{
                redisTemplate.opsForSet().remove(Constant.CHECKER.IP_LIST_NAME,StringUtil.trim(setting.getIp()));
            }

        }
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
     * 从数据库中获取数据fi
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
