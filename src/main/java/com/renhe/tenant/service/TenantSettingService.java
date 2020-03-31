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

    @Autowired
    TenantProductService tenantProductService;

    public int save(TenantSetting setting){
        return  mapper.save(setting);

    }

    public int update(TenantSetting setting){
        TenantSetting _tmp = this.findById(setting.getId());
        return mapper.update(setting);

    }

    public TenantSetting findById(String tenantId){
        return mapper.findById(tenantId);
    }

    public List<TenantSetting> querByParams(TenantSettingVo vo){
        return mapper.queryByParams(vo);
    }






    /**
     * 删除缓存信息
     */
    public void clearCache(){
        redisTemplate.delete(TENANT_IP_KEY);
    }

    private static final String TENANT_IP_KEY="CACHE_TENANT_IP";


}
