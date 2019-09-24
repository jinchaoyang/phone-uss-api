package com.renhe.tenant.mapper;

import com.renhe.tenant.entity.TenantSetting;
import com.renhe.tenant.vo.TenantSettingVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TenantSettingMapper  {

    int save(TenantSetting setting);

    int update(TenantSetting setting);

    TenantSetting findById(String id);

    List<TenantSetting> queryByParams(TenantSettingVo vo);
}
