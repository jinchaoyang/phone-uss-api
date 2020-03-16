package com.renhe.security.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.renhe.security.entity.Resource;
import com.renhe.security.mapper.ResourceMapper;
import com.renhe.security.vo.ResourceVo;
import com.renhe.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


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

    public List<Resource> buildTree(List<Resource> resourceList){
        List<Resource> rootNodes = this.queryNodesByLevel(0);
        List<Resource> secondNodes = this.queryNodesByLevel(1);
        List<Resource> thirdNodes = this.queryNodesByLevel(2);
        List<Resource> nodes = this.assemblySecondNodes(secondNodes,thirdNodes);
        List<Resource> result = this.assemblySecondNodes(rootNodes,nodes);
        return result;
    }

    public List<Resource> queryNodesByLevel(int level){
        ResourceVo vo = new ResourceVo();
        vo.setLevel(level);
        return this.queryByParams(vo);
    }

    public List<Resource> assemblySecondNodes(List<Resource> secondList,List<Resource> thirdList){
        List<Resource> map = new ArrayList<>();
        Map<String,Resource> dics = assemblyToMap(secondList);
        Map<String,List<Resource>> result = new LinkedHashMap<>();
        String parentId = null;
        for(Resource resource : thirdList){
            parentId = resource.getParentId();
            if(StringUtil.isPresent(parentId)){
               List<Resource> nodes = result.getOrDefault(parentId,new ArrayList<Resource>());
               nodes.add(resource);
               result.put(parentId,nodes);
            }
        }
        dics.keySet().stream().forEach(key -> {
            Resource resource = dics.get(key);
            resource.setChildren(result.getOrDefault(key,new ArrayList<>()));
            map.add(resource);
        });

        return map;
    }

    public Map<String,Resource> assemblyToMap(List<Resource> resourceList){
        Map<String,Resource> map = new HashMap<>();
        for(Resource resource : resourceList){
            map.put(resource.getId(),resource);
        }
        return map;
    }

    public JSONArray assemblyTree(List<Resource> resourceList){
        return JSONArray.parseArray(JSON.toJSONString(resourceList));
    }


}
