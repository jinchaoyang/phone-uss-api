package com.renhe.controller;

import com.alibaba.fastjson.JSONArray;
import com.renhe.base.Result;
import com.renhe.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/stat")
public class StatController  {

   @Autowired
   VerifyService verifyService;

    /**
     * 根据IP查询IP的统计信息
     * @param ip
     * @return
     */
    @CrossOrigin
    @RequestMapping(value="/cus")
    public JSONArray statByIp(@RequestParam(value = "ip")String ip){
       JSONArray array = verifyService.getAllByDicName(ip);
       return array;

    }


    @CrossOrigin
    @GetMapping(value="/all")
    public Result<JSONArray> statAllByIp(@RequestParam(value="ip")String ip){
        Result<JSONArray> result = new Result<>();
        int rcode = -1;
        try {
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
