package com.renhe.cdr.controller;

import com.renhe.auth.utils.TokenUtil;
import com.renhe.base.Result;
import com.renhe.cdr.service.CdrService;
import com.renhe.cdr.vo.BlackRecordVo;
import com.renhe.cdr.vo.CdrVo;
import com.renhe.cdr.vo.VipRecordVo;
import com.renhe.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping(value="/cdr")
public class CdrController {


    @Autowired
    CdrService cdrService;


    @GetMapping(value="/black")
    public Result<Page<BlackRecordVo>> fetchBlackList(CdrVo vo, HttpServletRequest request){
        Result<Page<BlackRecordVo>> result = new Result<>();
        int rcode = -1;
        try {
            String token = request.getHeader("Authorization");
            if(StringUtil.isPresent(token)) {
               String  tenantId = TokenUtil.getTenantId(token);
               vo.setTenantId(tenantId);
            }
            Page<BlackRecordVo> data = cdrService.queryBlackSheets(vo);
            result.setData(data);
            rcode = 0;
        }catch(Exception e){
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        return result;
    }


    @GetMapping(value="/vip")
    public Result<Page<VipRecordVo>> fetchVipList(CdrVo vo, HttpServletRequest request){
        Result<Page<VipRecordVo>> result = new Result<>();
        int rcode = -1;
        try {
            String token = request.getHeader("Authorization");
            if(StringUtil.isPresent(token)) {
                String  tenantId = TokenUtil.getTenantId(token);
                vo.setTenantId(tenantId);
            }
            Page<VipRecordVo> data = cdrService.queryVipSheets(vo);
            result.setData(data);
            rcode = 0;
        }catch(Exception e){
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        return result;
    }

}
