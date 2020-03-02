package com.renhe.tenant.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renhe.tenant.entity.TenantTrade;
import com.renhe.tenant.mapper.TenantTradeMapper;
import com.renhe.tenant.vo.TenantTradeVo;
import com.renhe.utils.Constant;
import com.renhe.utils.IDUtil;
import com.renhe.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TenantTradeService {

    @Autowired
    TenantTradeMapper mapper;


    @Autowired
    TenantProductService tenantProductService;

    @Autowired
    StringRedisTemplate redisTemplate;



    public PageInfo<TenantTrade> queryPager(TenantTradeVo tradeVo, int pageNo, int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        List<TenantTrade> tenantList = mapper.queryByParams(tradeVo);
        PageInfo<TenantTrade> pageInfo = new PageInfo<>(tenantList);
        return pageInfo;

    }

    public List<TenantTrade> queryByParams(TenantTradeVo vo){
        return mapper.queryByParams(vo);
    }

    public String  save(TenantTrade tenantTrade){
        String tradeId = null;
        int result = 0;
        tenantTrade.setId(IDUtil.generate());
        tenantTrade.setAmount(tenantTrade.getAmount()*10000);
        result = mapper.save(tenantTrade);
        if(result>0){
            Map<String,Object> params = new HashMap<>();
            params.put("tenantId",tenantTrade.getTenantId());
            params.put("amount",tenantTrade.getAmount());
            if(tenantTrade.getTradeType().equals("1")){ //充值
                this.charge(params);
            }else{
                this.consume(params);
            }
            tradeId = tenantTrade.getId();
        }
        return tradeId;
    }

    public int charge(Map<String,Object> params){
        int result =  mapper.charge(params);
        this.updateCache(params,1);
        tenantProductService.activeTenantProduct(params.get("tenantId").toString());
        return result;
    }

    public int consume(Map<String,Object> params){
        int result =  mapper.consume(params);
        this.updateCache(params,2);
        return result;

    }

    public void updateCache(Map<String,Object> params,int type){
        String tenantId = StringUtil.trim(params.get("tenantId"));
        long amount = Long.parseLong(StringUtil.trim(params.getOrDefault("amount","0")));
        String key = Constant.ACC.ACC_PREFIX+tenantId;
        if(type==1) {
            redisTemplate.opsForHash().increment(key, "balance", amount);
        }else{
            redisTemplate.opsForHash().increment(key, "balance", -amount);
        }
    }



}
