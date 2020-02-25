package com.renhe.tenant.mapper;



import com.renhe.tenant.entity.TenantBill;
import com.renhe.tenant.vo.TenantBillVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TenantBillMapper {

    List<TenantBill> queryByParams(TenantBillVo vo);

    int save(TenantBill tenantBill);
}
