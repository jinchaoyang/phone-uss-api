package com.renhe.security.mapper;

import com.renhe.security.entity.Resource;
import com.renhe.security.vo.ResourceVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ResourceMapper {

    Resource findById(String id);

    List<Resource> queryByParams(ResourceVo vo);

    int save(Resource resource);

    int update(Resource resource);

    int destroy(String resourceId);
}
