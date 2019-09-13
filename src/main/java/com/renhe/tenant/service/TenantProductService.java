package com.renhe.tenant.service;

import com.renhe.tenant.entity.TenantProduct;
import com.renhe.tenant.mapper.TenantProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantProductService {

    @Autowired
    TenantProductMapper tenantProductMapper;


    public List<TenantProduct> findByTenantId(String tenantId){
        return tenantProductMapper.findByTenantId(tenantId);
    }

    TenantProduct findByTenantIdAndType(String tenantId,String type){
        return tenantProductMapper.findByTenantIdAndType(tenantId,type);
    }

    public  int save(TenantProduct tenantProduct){
        return tenantProductMapper.save(tenantProduct);
    }

    public int update(TenantProduct tenantProduct){
        return tenantProductMapper.update(tenantProduct);
    }

    public int destroy(String id){
        return tenantProductMapper.destroy(id);
    }

    public  TenantProduct findById(String id){
        return tenantProductMapper.findById(id);
    }

}
