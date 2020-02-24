package com.renhe.tenant.mapper;

import com.renhe.tenant.entity.TenantTrade;
import com.renhe.tenant.vo.TenantTradeVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TenantTradeMapper {

    List<TenantTrade> queryByParams(TenantTradeVo vo);

    int save(TenantTrade tenantTrade);


}
