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

import java.time.LocalDateTime;
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

    public TenantProduct findByTenantIdAndType(String tenantId,String type){
        TenantProductVo vo = new TenantProductVo();
        vo.setTenantId(tenantId);
        vo.setProductType(type);
        List<TenantProduct> products = this.queryByParams(vo);
        if(null!=products && !products.isEmpty()){
            return products.get(0);
        }
        return null;
    }

    public  int save(TenantProduct tenantProduct){
        String pattern = "yyyy-MM-dd HH:mm";
        tenantProduct.setId(IDUtil.generate());
        tenantProduct.setFee(tenantProduct.getFee()*100);
        LocalDateTime begin = DateUtil.getDateTime(tenantProduct.getEffectAt(),pattern);
        LocalDateTime end = DateUtil.plusMonths(begin,tenantProduct.getDuration());
        tenantProduct.setExpireAt(DateUtil.localDateTimeFormat(end,pattern));
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

    public int renew(TenantProduct tenantProduct){
        int result = 0;
        String pattern = "yyyy-MM-dd HH:mm";
        TenantProduct _product = this.findByTenantIdAndType(tenantProduct.getTenantId(),tenantProduct.getProductType());
        if(null!=_product){
           String now =  DateUtil.getNow(pattern);
           String beginAt = now;
           if(now.compareTo(_product.getExpireAt())<=0){ //未过期
                beginAt = _product.getExpireAt();
           }
            LocalDateTime begin = DateUtil.getDateTime(beginAt,pattern);
            LocalDateTime end = DateUtil.plusMonths(begin,tenantProduct.getDuration());
            _product.setExpireAt(DateUtil.localDateTimeFormat(end,pattern));
            result = this.update(_product);

        }
        return result;
    }


}
