package com.renhe.security.service;

import com.renhe.security.entity.Resource;
import com.renhe.security.mapper.ResourceMapper;
import com.renhe.security.vo.ResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ResourceService {

    @Autowired
    ResourceMapper resourceMapper;


    public List<Resource> queryByParams(ResourceVo vo){
        return resourceMapper.queryByParams(vo);
    }

    public int save(Resource resource){
        return resourceMapper.save(resource);
    }

    public int update(Resource resource){
        return resourceMapper.update(resource);
    }

    public int destroy(String id){
        return resourceMapper.destroy(id);
    }

    public Resource findById(String id){
        return resourceMapper.findById(id);
    }


}
