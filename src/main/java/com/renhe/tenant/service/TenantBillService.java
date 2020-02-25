package com.renhe.tenant.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renhe.tenant.entity.TenantBill;
import com.renhe.tenant.mapper.TenantBillMapper;
import com.renhe.tenant.vo.TenantBillVo;
import com.renhe.utils.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantBillService {

    @Autowired
    TenantBillMapper mapper;


    public PageInfo<TenantBill> queryPager(TenantBillVo billVo, int pageNo, int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        List<TenantBill> tenantList = mapper.queryByParams(billVo);
        PageInfo<TenantBill> pageInfo = new PageInfo<>(tenantList);
        return pageInfo;

    }

    public List<TenantBill> queryByParams(TenantBillVo vo){
        return mapper.queryByParams(vo);
    }

    public int save(TenantBill tenantBill){
        tenantBill.setId(IDUtil.generate());
        return mapper.save(tenantBill);
    }
}
