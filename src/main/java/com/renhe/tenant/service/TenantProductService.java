package com.renhe.tenant.service;

import com.renhe.tenant.entity.TenantProduct;
import com.renhe.tenant.mapper.TenantProductMapper;
import com.renhe.tenant.vo.TenantProductVo;
import com.renhe.utils.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantProductService {

    @Autowired
    TenantProductMapper tenantProductMapper;


    public List<TenantProduct> findByTenantId(String tenantId){
        TenantProductVo vo = new TenantProductVo();
        vo.setTenantId(tenantId);
        return this.queryByParams(vo);
    }

    TenantProduct findByTenantIdAndType(String tenantId,String type){
        return tenantProductMapper.findByTenantIdAndType(tenantId,type);
    }

    public  int save(TenantProduct tenantProduct){
        tenantProduct.setId(IDUtil.generate());
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

    public List<TenantProduct> queryByParams(TenantProductVo vo){
        return tenantProductMapper.queryByParams(vo);
    }

    public List<TenantProduct> queryBlackFilterProduct(){
        TenantProductVo vo = new TenantProductVo();
        vo.setProductType("1001");
        vo.setStatus("1");
        return this.queryByParams(vo);
    }


}
