package com.renhe.security.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.renhe.base.Result;
import com.renhe.security.entity.Resource;
import com.renhe.security.entity.Role;
import com.renhe.security.service.ResourceService;
import com.renhe.security.service.RoleService;
import com.renhe.security.vo.ResourceVo;
import com.renhe.security.vo.RoleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value="/sys/resource")
public class ResourceController {


    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    ResourceService resourceService;

    /**
     * 资源信息列表
     * @param query
     * @return
     */
    @GetMapping(value="/list")
    public Result<List<Resource>> index(ResourceVo query){
        Result<List<Resource>> result = new Result<>();
        int rcode = -1;
        List<Resource> resources = resourceService.queryByParams(query);
        if(null!=resources){
            result.setData(resources);
            rcode =  0 ;
        }else{
            result.setMessage("no record");
        }
        result.setCode(rcode);
        return result;
    }

    /**
     * 创建角色信息
     * @param resource
     * @return
     */
    @PostMapping("")
    public Result<Integer> save(@RequestBody Resource resource){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            resourceService.save(resource);
            rcode = 0;
        }catch(Exception e){
            result.setMessage(e.getMessage());
            logger.error("[saveException]: role -> {}", JSON.toJSONString(resource),e);
        }
        result.setCode(rcode);
        return result;
    }


    /**
     * 更新对象信息
     * @param resource
     * @return
     */
    @PutMapping(value="/{id}")
    public Result<Integer> update(@PathVariable("id") String id,@RequestBody Resource resource){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            int count = resourceService.update(resource);
            if(count>0){
                rcode = 0;
                result.setData(count);
                result.setMessage("success");
            }else{
                result.setMessage("resource not exists");
            }
        }catch(Exception e){
            logger.error("[updateException]: role -> {}",JSON.toJSONString(resource),e);
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        return result;
    }



    /**
     * 删除资源信息
     * @param id
     * @return
     */
    @DeleteMapping(value="/{id}")
    public Result<Integer> destroy(@PathVariable String id){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            int count = resourceService.destroy(id);
            if(count>0){
                rcode = 0;
            }else{
                result.setMessage("no record found");
            }

        }catch (Exception e){
            result.setMessage(e.getMessage());
            logger.error("[destroyException]: id -> {}", id,e);
        }
        result.setCode(rcode);
        return result;

    }


    /**
     * 查看资源信息
     * @param id
     * @return
     */
    @GetMapping(value="/{id}")
    public Result<Resource> show(@PathVariable String id){
        Result<Resource> result = new Result<>();
        int rcode = -1;
        try{
            Resource resource = resourceService.findById(id);
            result.setData(resource);
            rcode = 0;
        }catch (Exception e){
            result.setMessage(e.getMessage());
            logger.error("[findException]: id -> {}", id,e);
        }
        result.setCode(rcode);
        return result;

    }
}
