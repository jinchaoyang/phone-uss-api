package com.renhe.tenant.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renhe.tenant.entity.Tenant;
import com.renhe.tenant.entity.TenantProduct;
import com.renhe.tenant.mapper.TenantMapper;
import com.renhe.tenant.vo.TenantVo;
import com.renhe.utils.Constant;
import com.renhe.utils.DateUtil;
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
        return tenantMapper.save(tenant);
    }


    public int update(Tenant tenant){
        return tenantMapper.update(tenant);
    }

    public int destroy(String id){
        return tenantMapper.destroy(id);
    }


    public boolean allowVerify(String ip,String productType){
        String tenantId = tenantSettingService.getTenantByIp(ip);
        boolean result  = false;
        if(StringUtil.isPresent(tenantId)){
            if(productType.equals("1001")) {
                result = redisTemplate.opsForSet().isMember(Constant.CHECKER.IP_LIST_NAME, ip);
            }

        }

        return result;
    }



}
