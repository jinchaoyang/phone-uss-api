package com.renhe.qc.controller;

import com.renhe.auth.utils.TokenUtil;
import com.renhe.base.Result;
import com.renhe.qc.entity.QcCdr;
import com.renhe.qc.service.QcCdrService;
import com.renhe.qc.vo.QcCdrVo;
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
@RequestMapping(value="/qc/cdr")
public class QcCdrController {

    @Autowired
    QcCdrService qcCdrService;


    @GetMapping(value="/list")
    public Result<Page<QcCdr>> fetchCdrList(QcCdrVo vo, HttpServletRequest request){
        Result<Page<QcCdr>> result = new Result<>();
        int rcode = -1;
        try {
            String token = request.getHeader("Authorization");
            if(StringUtil.isPresent(token)) {
               String tenantId = TokenUtil.getTenantId(token);
               vo.setTenantId(tenantId);
            }else{
                vo.setTenantId("unauthorized");
            }
            Page<QcCdr> data = qcCdrService.queryCallSheets(vo);
            result.setData(data);
            rcode = 0;
        }catch(Exception e){
            result.setMessage(e.getMessage());
        }
        result.setCode(rcode);
        return result;
    }



}
