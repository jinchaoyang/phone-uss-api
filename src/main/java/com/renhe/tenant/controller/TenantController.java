package com.renhe.tenant.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.renhe.base.Result;
import com.renhe.security.entity.User;
import com.renhe.security.vo.UserVo;
import com.renhe.tenant.entity.Tenant;
import com.renhe.tenant.service.TenantService;
import com.renhe.tenant.vo.TenantVo;
import com.renhe.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value="/tenant")
public class TenantController {


    private static final Logger logger = LoggerFactory.getLogger(TenantController.class);

    @Autowired
    TenantService tenantService;



    /**
     * 用户信息列表
     * @param query
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping(value="/list")
    public Result<PageInfo<Tenant>> index(TenantVo query, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "15") int pageSize){
        Result<PageInfo<Tenant>> result = new Result<>();
        int rcode = -1;
        PageInfo<Tenant> tenantPageInfo = tenantService.queryPager(query,pageNo,pageSize);
        if(null!=tenantPageInfo){
            result.setData(tenantPageInfo);
            rcode =  0 ;
        }else{
            result.setMessage("no record");
        }
        result.setCode(rcode);
        return result;
    }

    /**
     * 创建租户信息
     * @param tenant
     * @return
     */
    @PostMapping("")
    public Result<Integer> save(@RequestBody Tenant tenant){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            tenantService.save(tenant);
            rcode = 0;
        }catch(Exception e){
            result.setMessage(e.getMessage());
            logger.error("[saveException]: tenant -> {}", JSON.toJSONString(tenant),e);
        }
        result.setCode(rcode);
        return result;
    }

    /**
     * 获取用户的基本信息
     * @param id
     * @return
     */
    @GetMapping(value="/{id}")
    public Result<Tenant> findById(@PathVariable String id){
        Result<Tenant> result = new Result<>();
        int rcode = -1;
        try{
            Tenant tenant = tenantService.findById(id);
            if(null!=tenant){
                rcode = 0;
                result.setData(tenant);
                result.setMessage("success");
            }else{
                result.setMessage("tenant not exists.");
            }
        }catch(Exception e){
            logger.error("[findTenantException]: id -> {}",id,e);
        }
        result.setCode(rcode);
        return result;
    }


    /**
     * 更新对象信息
     * @param tenant
     * @return
     */
    @PutMapping(value="/{id}")
    public Result<Integer> update(@RequestBody Tenant tenant){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            int count = tenantService.update(tenant);
            if(count>0){
                rcode = 0;
                result.setData(count);
                result.setMessage("success");
            }else{
                result.setMessage("tenant not exists");
            }
        }catch(Exception e){
            logger.error("[updateException]: tenant -> {}",JSON.toJSONString(tenant),e);
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        return result;
    }



    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @DeleteMapping(value="/{id}")
    public Result<Integer> destroy(@PathVariable String id){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            int count = tenantService.destroy(id);
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
     * 验证用户名信息是否已经存在
     *
     * @return
     */
    @GetMapping(value="/tenantCodeCheck")
    public Result<Boolean> tenantCodeCheck(TenantVo vo){
        Result<Boolean> result = new Result<>();
        int rcode = -1;
        try{
            List<Tenant> tenants = tenantService.queryByParams(vo);
            boolean state = false;
            if(null==tenants || tenants.isEmpty()){
                state = true;
            }else{
                if(StringUtil.isPresent(vo.getId())){
                    Tenant tenant = tenants.get(0);
                    if(vo.getId().equals(tenant.getId())){
                        state = true;
                    }
                }
            }
            result.setData(state);
            rcode = 0;

        }catch (Exception e){
            result.setMessage(e.getMessage());
            logger.error("[tenantCodeCheckException]: vo -> {}",JSON.toJSONString(vo));
        }
        result.setCode(rcode);
        return result;
    }





}
