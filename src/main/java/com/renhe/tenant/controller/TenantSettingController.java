package com.renhe.tenant.controller;

import com.alibaba.fastjson.JSON;
import com.renhe.base.Result;
import com.renhe.tenant.entity.TenantSetting;
import com.renhe.tenant.service.TenantSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping(value="/tenant/setting")
public class TenantSettingController {

    private static final Logger logger  = LoggerFactory.getLogger(TenantSettingController.class);

    @Autowired
    TenantSettingService service;

    /**
     * 根据ID查询租户配置信息
     * @param id
     * @return
     */
    @GetMapping(value="/{id}")
    public Result<TenantSetting> findById(@PathVariable String id){
        Result<TenantSetting> result = new Result<>();
        int rcode = -1;
        try{
            TenantSetting setting = service.findById(id);
            result.setData(setting);
            rcode = 0;

        }catch (Exception e){
            logger.error("[findById]: id -> {}",id,e);
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        return result;
    }


    /**
     * 更新对象
     * @param setting
     * @return
     */
    @PutMapping(value="/{id}")
    public Result<Integer> update(@PathVariable("id") String id,@RequestBody  TenantSetting setting){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            TenantSetting tenantSetting = service.findById(id);
            int data = -1;
            if(null==tenantSetting){
                data = service.save(setting);
            }else {
                data = service.update(setting);
            }
            result.setData(data);
            rcode = 0;

        }catch (Exception e){
            logger.error("[save]: setting -> {}", JSON.toJSONString(setting),e);
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        return result;
    }
}
