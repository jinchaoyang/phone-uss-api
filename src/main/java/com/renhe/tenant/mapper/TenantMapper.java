package com.renhe.tenant.mapper;

import com.renhe.tenant.entity.Tenant;
import com.renhe.tenant.vo.TenantVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TenantMapper {

    Tenant findById(String id);

    Tenant findByCode(String tenantCode);

    int save(Tenant tenant);

    int update(Tenant tenant);

    int destroy(String id);

    List<Tenant> queryByParams(TenantVo tenantVo);
}
