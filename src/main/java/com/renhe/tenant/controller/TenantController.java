package com.renhe.tenant.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.renhe.auth.utils.TokenUtil;
import com.renhe.base.Result;
import com.renhe.service.VerifyService;
import com.renhe.tenant.entity.Tenant;
import com.renhe.tenant.service.TenantService;
import com.renhe.tenant.vo.TenantVo;
import com.renhe.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value="/tenant")
public class TenantController {


    private static final Logger logger = LoggerFactory.getLogger(TenantController.class);

    @Autowired
    TenantService tenantService;

    @Autowired
    VerifyService verifyService;



    /**
     * 获取租户的基本信息
     * @return
     */
    @GetMapping(value="/info")
    public Result<JSONObject> findById(HttpServletRequest request){
        Result<JSONObject> result = new Result<>();
        int rcode = -1;
        String tenantId = null;
        try{
            String token = request.getHeader("Authorization");
            if(StringUtil.isPresent(token)) {
                tenantId = TokenUtil.getTenantId(token);
                Tenant tenant = tenantService.findById(tenantId);
                if(null!=tenant){
                    JSONObject cache = tenantService.getTenantCache(tenantId);
                    rcode = 0;
                    JSONObject data = JSONObject.parseObject(JSON.toJSONString(tenant));
                    data.putAll(cache);
                    result.setData(data);
                    result.setMessage("success");
                }else{
                    result.setMessage("tenant not exists.");
                }
            }

        }catch(Exception e){
            logger.error("[findTenantException]: tenantId -> {}",tenantId,e);
        }
        result.setCode(rcode);
        return result;
    }


    @GetMapping("/stat")
    public Result<JSONArray> statAllByIp(HttpServletRequest request){
        Result<JSONArray> result = new Result<>();
        int rcode = -1;
        try {
            String token = request.getHeader("Authorization");
            String ip = null;
            if(StringUtil.isPresent(token)) {
              String  tenantId = TokenUtil.getTenantId(token);
              ip = tenantService.getTenantIp(tenantId);
            }
            JSONArray array = verifyService.statAllByDicName(ip);
            result.setData(array);
            rcode = 0;
        }catch(Exception e){
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        return result;
    }





}
