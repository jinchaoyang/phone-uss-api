package com.renhe.tenant.service;

import com.renhe.tenant.entity.TenantProduct;
import com.renhe.tenant.mapper.TenantProductMapper;
import com.renhe.tenant.vo.TenantProductVo;
import com.renhe.utils.Constant;
import com.renhe.utils.DateUtil;
import com.renhe.utils.IDUtil;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantProductService {

    @Autowired
    TenantProductMapper tenantProductMapper;

    @Autowired
    StringRedisTemplate redisTemplate;


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
        return  tenantProductMapper.save(tenantProduct);
    }

    public boolean blackValid(String tenantId){
        TenantProductVo vo = new TenantProductVo();
        vo.setProductType("1001");
        vo.setStatus("1");
        vo.setTenantId(tenantId);
        List<TenantProduct> tenantProducts = this.queryBlackFilterProduct();
        if(null!=tenantProducts && !tenantProducts.isEmpty()){
            String today = DateUtil.getNow();
            TenantProduct product = tenantProducts.get(0);
            if(null!=product && product.getExpireAt().compareTo(today)>-1){
                return true;
            }

        }
        return false;

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
