package com.renhe.security.mapper;

import com.renhe.security.entity.Role;
import com.renhe.security.vo.RoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface RoleMapper {

    Role findById(String id);

    List<Role> queryByParams(RoleVo vo);

    int save(Role role);

    int update(Role role);

    int destroy(String id);

    int bind(Map<String,Object> params);

    int release(String roleId);


}
