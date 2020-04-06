package com.renhe.security.mapper;


import com.renhe.security.entity.TenantUser;
import com.renhe.security.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface TenantUserMapper {

    TenantUser findByUserNameAndPassword(Map<String,Object> params);

    TenantUser findById(String id);

    List<TenantUser> queryByParams(UserVo vo);

    int save(TenantUser user);

    int update(TenantUser user);

    int destroy(String id);





}
