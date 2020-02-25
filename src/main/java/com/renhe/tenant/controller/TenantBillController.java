package com.renhe.tenant.controller;

import com.github.pagehelper.PageInfo;
import com.renhe.base.Result;
import com.renhe.tenant.entity.TenantBill;
import com.renhe.tenant.service.TenantBillService;
import com.renhe.tenant.vo.TenantBillVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping(value ="/tenant/bill")
public class TenantBillController {


    private final static Logger logger = LoggerFactory.getLogger(TenantBillController.class);

    @Autowired
    TenantBillService billService;

    @GetMapping(value="/list")
    public Result<PageInfo<TenantBill>> index(TenantBillVo query, @RequestParam(defaultValue = "1") int pageNo, @RequestParam(defaultValue = "15") int pageSize){
        Result<PageInfo<TenantBill>> result = new Result<>();
        int rcode = -1;
        PageInfo<TenantBill> tenantPageInfo = billService.queryPager(query,pageNo,pageSize);
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
