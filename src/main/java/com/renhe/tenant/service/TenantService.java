package com.renhe.tenant.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.renhe.tenant.entity.Tenant;
import com.renhe.tenant.mapper.TenantMapper;
import com.renhe.tenant.vo.TenantVo;
import com.renhe.utils.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {

    @Autowired
    TenantMapper tenantMapper;

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


}
