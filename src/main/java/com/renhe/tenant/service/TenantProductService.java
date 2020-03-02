package com.renhe.tenant.service;

import com.renhe.tenant.entity.Tenant;
import com.renhe.tenant.entity.TenantBill;
import com.renhe.tenant.entity.TenantProduct;
import com.renhe.tenant.entity.TenantTrade;
import com.renhe.tenant.mapper.TenantProductMapper;
import com.renhe.tenant.vo.TenantProductVo;
import com.renhe.utils.Constant;
import com.renhe.utils.DateUtil;
import com.renhe.utils.IDUtil;
import org.apache.tomcat.util.bcel.Const;
import org.bson.codecs.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TenantProductService {

    @Autowired
    TenantProductMapper tenantProductMapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    TenantService tenantService;

    @Autowired
    TenantTradeService tradeService;

    @Autowired
    TenantBillService billService;


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
            BigDecimal bigDecimal = new BigDecimal(tenantProduct.getFeeDesc()).multiply(new BigDecimal(10000));
            _product.setFee(bigDecimal.intValue());

            result = this.update(_product);

        }
        return result;
    }


    public Map<String,String> buyProduct(TenantProduct tenantProduct,int type){
        Map<String,String> result = new HashMap<>();
        String message = null;
        int code = 0;
        Tenant tenant = tenantService.findById(tenantProduct.getTenantId());
        long total = tenant.getBalance()+tenant.getOverdraft();
        BigDecimal bigDecimal = new BigDecimal(tenantProduct.getFeeDesc()).multiply(new BigDecimal(10000));
        tenantProduct.setFee(bigDecimal.intValue());
        if(tenantProduct.getFeeType().equals("2") && total < 10*10000){ //按量计费,小于10元不能开通
            message = "账户可用余额必需大于10元";
            code = 1001;
        }else{
            long fee = tenantProduct.getFee()* tenantProduct.getDuration();
            if(total<fee){
                message = "余额不足，请先充值";
                code = 1002;
            }
        }

        if(code==0){
            if(type==1) {
                this.save(tenantProduct);
            }else{
                this.renew(tenantProduct);
            }
             long amount = tenantProduct.getFee()*tenantProduct.getDuration();
            if(tenantProduct.getFeeType().equals("2")){
                amount = 0l;
            }
            TenantTrade tradeLog = new TenantTrade();
            tradeLog.setCreatorId(tenantProduct.getCreatorId());
            tradeLog.setAmount(amount/10000);
            tradeLog.setTenantId(tenantProduct.getTenantId());
            tradeLog.setTradeType("2");
            String tradeId = tradeService.save(tradeLog);

            TenantBill bill = new TenantBill();
            bill.setId(IDUtil.generate());
            bill.setAmount(amount);
            bill.setBillType("1");
            bill.setCreatorId(tenantProduct.getCreatorId());
            bill.setTenantId(tenantProduct.getTenantId());
            bill.setDuration(tenantProduct.getDuration());
            bill.setFee(tenantProduct.getFee());
            bill.setProductCode(tenantProduct.getProductType());
            bill.setTradeId(tradeId);
            billService.save(bill);

            this.updateCache(tenantProduct,tenant,0);

        }else{
            this.updateCache(tenantProduct,tenant,-1);
        }
        result.put("code",code+"");
        result.put("message",message);
        return result;


    }

    /**
     * 0: 续费成功
     * -1: 续费失败
     * @param tenantProduct
     * @param tenant
     * @param type
     */
    public void updateCache(TenantProduct tenantProduct,Tenant tenant,int type){
        String key = Constant.ACC.ACC_PREFIX+tenantProduct.getProductType();
        if(type==0){
            redisTemplate.opsForSet().add(key,tenant.getIp());
        }else{
            redisTemplate.opsForSet().remove(key,tenant.getIp());
        }
    }


    public void activeTenantProduct(String tenantId){
        TenantProductVo vo = new TenantProductVo();
        vo.setTenantId(tenantId);
        vo.setFeeType("2");
        Tenant tenant = tenantService.findById(tenantId);
        List<TenantProduct> products = tenantProductMapper.queryByParams(vo);
        if(null!=products && !products.isEmpty() && null!=tenant){
            for(TenantProduct p : products){
                this.updateCache(p,tenant,0);
            }
        }
    }


    public boolean allowVerify(String productCode,String ip){
        String key = Constant.ACC.ACC_PREFIX+productCode;
        return redisTemplate.opsForSet().isMember(key,ip);
    }




}
