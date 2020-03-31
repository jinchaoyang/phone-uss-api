package com.renhe.tenant.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renhe.tenant.entity.Tenant;
import com.renhe.tenant.mapper.TenantMapper;
import com.renhe.tenant.vo.TenantVo;
import com.renhe.utils.Constant;
import com.renhe.utils.IDUtil;
import com.renhe.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {

    @Autowired
    TenantMapper tenantMapper;

    @Autowired
    TenantSettingService tenantSettingService;

    @Autowired
    TenantProductService tenantProductService;

    @Autowired
    StringRedisTemplate redisTemplate;

    public PageInfo<Tenant> queryPager(TenantVo tenantVo, int pageNo, int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        List<Tenant> tenantList = tenantMapper.queryByParams(tenantVo);
        PageInfo<Tenant> pageInfo = new PageInfo<>(tenantList);
        return pageInfo;

    }

    public List<Tenant> queryByParams(TenantVo tenantVo){
        return  tenantMapper.queryByParams(tenantVo);
    }

    public Tenant findById(String id){
        return tenantMapper.findById(id);
    }

    public int save(Tenant tenant){
        tenant.setId(IDUtil.generate());
        tenant.setStatus("1");
        tenant.setBalance(0l);
        if(null==tenant.getOverdraft()){
            tenant.setOverdraft(0l);
        }
        tenant.setOverdraft(tenant.getOverdraft()*10000);
        int result = tenantMapper.save(tenant);
        if(result >0 ){
            this.updateCache(tenant);
        }
        return result;
    }


    public int update(Tenant tenant){
        tenant.setOverdraft(tenant.getOverdraft()*10000);
        int result =  tenantMapper.update(tenant);

        if(StringUtil.isPresent(tenant.getIp())){
            this.updateCache(tenant);
        }

        return result;
    }

    public int destroy(String id){

        int result =  tenantMapper.destroy(id);
        if(result>0){
            this.removeCache(id);
        }
        return result;
    }


    public void updateCache(Tenant tenant){
        String key = Constant.ACC.ACC_PREFIX+tenant.getId();
        redisTemplate.opsForHash().put(Constant.ACC.ACC_IPS_DICNAME,tenant.getIp(),tenant.getId());
        redisTemplate.opsForHash().put(key,"ip",tenant.getIp());
        redisTemplate.opsForHash().put(key,"overdraft",tenant.getOverdraft().toString());

    }

    public void removeCache(String tenantId){
        String key =  Constant.ACC.ACC_PREFIX + tenantId;
        String ip = redisTemplate.opsForHash().get(key,"ip").toString();
        redisTemplate.opsForHash().delete(Constant.ACC.ACC_IPS_DICNAME,ip);
        redisTemplate.delete(key);


    }






}
