package com.renhe.tenant.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renhe.tenant.entity.TenantTrade;
import com.renhe.tenant.mapper.TenantTradeMapper;
import com.renhe.tenant.vo.TenantTradeVo;
import com.renhe.utils.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public int save(TenantTrade tenantTrade){
        tenantTrade.setId(IDUtil.generate());
        return mapper.save(tenantTrade);
    }



}
