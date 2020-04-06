package com.renhe.tenant.controller;

import com.alibaba.fastjson.JSON;
import com.renhe.auth.utils.TokenUtil;
import com.renhe.base.Result;
import com.renhe.tenant.entity.TenantSetting;
import com.renhe.tenant.service.TenantSettingService;
import com.renhe.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@CrossOrigin
@RestController
@RequestMapping(value="/tenant/setting")
public class TenantSettingController {

    private static final Logger logger  = LoggerFactory.getLogger(TenantSettingController.class);

    @Autowired
    TenantSettingService service;

    /**
     * 根据ID查询租户配置信息
     * @return
     */
    @GetMapping(value="/info")
    public Result<TenantSetting> findById(HttpServletRequest request){
        Result<TenantSetting> result = new Result<>();
        int rcode = -1;
        String tenantId = null;
        try{
            String token = request.getHeader("Authorization");
            if(StringUtil.isPresent(token)) {
                tenantId = TokenUtil.getTenantId(token);
            }
            TenantSetting setting = service.findById(tenantId);
            result.setData(setting);
            rcode = 0;

        }catch (Exception e){
            logger.error("[findById]: id -> {}",tenantId,e);
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
    @PutMapping(value="/update")
    public Result<Integer> update(HttpServletRequest request,@RequestBody  TenantSetting setting){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        String tenantId = null;
        try{
            String token = request.getHeader("Authorization");
            if(StringUtil.isPresent(token)) {
                tenantId = TokenUtil.getTenantId(token);
            }
            TenantSetting tenantSetting = service.findById(tenantId);
            int data = -1;
            if(null==tenantSetting){
                setting.setId(tenantId);
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
