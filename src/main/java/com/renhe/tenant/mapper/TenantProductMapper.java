package com.renhe.tenant.mapper;

import com.renhe.tenant.entity.TenantProduct;
import com.renhe.tenant.vo.TenantProductVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TenantProductMapper {

    TenantProduct findByTenantIdAndType(String tenantId,String type);

    int save(TenantProduct tenantProduct);

    int update(TenantProduct tenantProduct);

    int destroy(String id);

    TenantProduct findById(String id);

    List<TenantProduct> queryByParams(TenantProductVo vo);


}
