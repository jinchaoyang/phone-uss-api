package com.renhe.tenant.service;

import com.renhe.tenant.entity.TenantSetting;
import com.renhe.tenant.mapper.TenantSettingMapper;
import com.renhe.tenant.vo.TenantSettingVo;
import com.renhe.utils.MD5;
import com.renhe.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantSettingService {

    @Autowired
    TenantSettingMapper mapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    TenantProductService tenantProductService;

    public int save(TenantSetting setting){
        if(StringUtil.isPresent(setting.getPassword())){
            String password = MD5.encode(setting.getUsername()+setting.getPassword());
            setting.setPassword(password);
        }
        return  mapper.save(setting);

    }

    public int update(TenantSetting setting){
        TenantSetting _tmp = this.findById(setting.getId());
        if(!_tmp.getPassword().equals(setting.getPassword())){
            setting.setPassword(MD5.encode(setting.getUsername()+setting.getPassword()));
        }
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
