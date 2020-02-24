package com.renhe.tenant.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.renhe.auth.utils.TokenUtil;
import com.renhe.base.Result;
import com.renhe.tenant.entity.TenantTrade;
import com.renhe.tenant.service.TenantTradeService;
import com.renhe.tenant.vo.TenantTradeVo;
import com.renhe.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping(value="/tenant/trade")
public class TenantTradeController {

    private final static Logger logger = LoggerFactory.getLogger(TenantTradeController.class);

    @Autowired
    TenantTradeService tradeService;

    @GetMapping(value="/list")
    public Result<PageInfo<TenantTrade>> index(TenantTradeVo query, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "15") int pageSize){
        Result<PageInfo<TenantTrade>> result = new Result<>();
        int rcode = -1;
        PageInfo<TenantTrade> tenantPageInfo = tradeService.queryPager(query,pageNo,pageSize);
        if(null!=tenantPageInfo){
            result.setData(tenantPageInfo);
            rcode =  0 ;
        }else{
            result.setMessage("no record");
        }
        result.setCode(rcode);
        return result;
    }

    @PostMapping("")
    public Result<Integer> save(@RequestBody TenantTrade tenantTrade, HttpServletRequest request){
        Result<Integer> result = new Result<>();
        int rcode = -1;
        try{
            String token = request.getHeader("Authorization");
            if(StringUtil.isPresent(token)){
                tenantTrade.setCreatorId(TokenUtil.getUserId(token));
            }
            tradeService.save(tenantTrade);
            rcode = 0;
        }catch(Exception e){
            result.setMessage(e.getMessage());
            logger.error("[saveException]: tenantTrade -> {}", JSON.toJSONString(tenantTrade),e);
        }
        result.setCode(rcode);
        return result;
    }
}
