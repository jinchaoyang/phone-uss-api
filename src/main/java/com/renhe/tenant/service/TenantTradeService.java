package com.renhe.tenant.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renhe.tenant.entity.TenantTrade;
import com.renhe.tenant.mapper.TenantTradeMapper;
import com.renhe.tenant.vo.TenantTradeVo;
import com.renhe.utils.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TenantTradeService {

    @Autowired
    TenantTradeMapper mapper;




    public PageInfo<TenantTrade> queryPager(TenantTradeVo tradeVo, int pageNo, int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        List<TenantTrade> tenantList = mapper.queryByParams(tradeVo);
        PageInfo<TenantTrade> pageInfo = new PageInfo<>(tenantList);
        return pageInfo;

    }

    public List<TenantTrade> queryByParams(TenantTradeVo vo){
        return mapper.queryByParams(vo);
    }

    @Transactional
    public String  save(TenantTrade tenantTrade){
        String tradeId = null;
        int result = 0;
        tenantTrade.setId(IDUtil.generate());
        tenantTrade.setAmount(tenantTrade.getAmount()*100);
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
        return mapper.charge(params);
    }

    public int consume(Map<String,Object> params){
        return mapper.consume(params);

    }



}
