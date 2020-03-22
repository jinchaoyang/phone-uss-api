package com.renhe.security.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.renhe.base.Result;
import com.renhe.security.entity.Resource;
import com.renhe.security.entity.Role;
import com.renhe.security.service.RoleService;
import com.renhe.security.vo.RoleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value="/sys/role")
public class RoleController  {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    /**
     * 角色信息列表
     * @param query
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping(value="/list")
    public Result<PageInfo<Role>> index(RoleVo query, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "15") int pageSize){
        Result<PageInfo<Role>> result = new Result<>();
        int rcode = -1;
        PageInfo<Role> rolePageInfo = roleService.queryPager(query,pageNo,pageSize);
        if(null!=rolePageInfo){
            result.setData(rolePageInfo);
            rcode =  0 ;
        }else{
            result.setMessage("no record");
        }
        result.setCode(rcode);
        return result;
    }

    /**
     * 创建角色信息
     * @param role
     * @return
     */
    @PostMapping("")
    public Result<Integer> save(@RequestBody Role role){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            roleService.save(role);
            rcode = 0;
        }catch(Exception e){
            result.setMessage(e.getMessage());
            logger.error("[saveException]: role -> {}", JSON.toJSONString(role),e);
        }
        result.setCode(rcode);
        return result;
    }


    /**
     * 更新对象信息
     * @param role
     * @return
     */
    @PutMapping(value="/{id}")
    public Result<Integer> update(@PathVariable("id") String id,@RequestBody Role role){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            int count = roleService.update(role);
            if(count>0){
                rcode = 0;
                result.setData(count);
                result.setMessage("success");
            }else{
                result.setMessage("role not exists");
            }
        }catch(Exception e){
            logger.error("[updateException]: role -> {}",JSON.toJSONString(role),e);
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        return result;
    }



    /**
     * 删除角色信息
     * @param id
     * @return
     */
    @DeleteMapping(value="/{id}")
    public Result<Integer> destroy(@PathVariable String id){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            int count = roleService.destroy(id);
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
     * 查看角色信息
     * @param id
     * @return
     */
    @GetMapping(value="/{id}")
    public Result<Role> show(@PathVariable String id){
        Result<Role> result = new Result<>();
        int rcode = -1;
        try{
            Role role = roleService.findById(id);
            if(null!=role){
               List<Resource> resources = roleService.findByRoleId(id);
               if(null!=resources && !resources.isEmpty()){
                  List<String> rids =  resources.stream().map(e -> e.getId()).collect(Collectors.toList());
                  role.setResourceIds(rids);
               }else{
                   role.setResourceIds(new ArrayList<>(0));
               }
            }
            result.setData(role);
            rcode = 0;
        }catch (Exception e){
            result.setMessage(e.getMessage());
            logger.error("[findException]: id -> {}", id,e);
        }
        result.setCode(rcode);
        return result;

    }
}
