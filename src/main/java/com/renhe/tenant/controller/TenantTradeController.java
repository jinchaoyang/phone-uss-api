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
    public Result<PageInfo<TenantTrade>> index(TenantTradeVo query, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "15") int pageSize,HttpServletRequest request){
        Result<PageInfo<TenantTrade>> result = new Result<>();
        int rcode = -1;
        String token = request.getHeader("Authorization");
        if(StringUtil.isPresent(token)) {
            query.setTenantId(TokenUtil.getTenantId(token));
        }
        if(StringUtil.isPresent(query.getBeginAt())){
            query.setBeginAt(query.getBeginAt()+" 00:00:00");
        }
        if(StringUtil.isPresent(query.getEndAt())){
            query.setEndAt(query.getEndAt()+" 23:59:59");
        }
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


}
