package com.renhe.controller;

import com.alibaba.fastjson.JSONObject;
import com.renhe.base.Result;
import com.renhe.service.VerifyService;
import com.renhe.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(value="/api/v1.0/black")
public class BlackListController {

    private static final Logger logger = LoggerFactory.getLogger(BlackListController.class);

    @Autowired
    VerifyService service;


    /**
     * 单个号码验证是否为黑名单
     * @param phone
     * @return
     */
    @GetMapping(value="/verify")
    public Result<JSONObject> verify(@RequestParam("phone") String phone){

        Result<JSONObject> result = new Result<>();
        if(!StringUtil.isPresent(phone)){
            result.setMessage("必要参数不能为空");
            result.setCode(-1);
        }else{
            try {
                boolean isBlack = service.verify(phone);
                JSONObject obj = new JSONObject();
                obj.put("result",isBlack?1:0);
                result.setCode(0);
                result.setData(obj);
            }catch (Exception e){
                logger.error("verify failed!",e);
                result.setCode(-1);
                result.setMessage(e.getMessage());
            }


        }
        return result;
    }

    /**
     * 批量验证多个号码是否为黑名单
     * @param phones
     * @return
     */
    @GetMapping(value="/batchVerify")
    public Result<JSONObject> batchVerify(@RequestParam("phones")String phones){
        Result<JSONObject> result = new Result<>();
        if(!StringUtil.isPresent(phones)){
            result.setMessage("必要参数不能为空");
            result.setCode(-1);
        }else{
            try {
                String[] arr = phones.split(",");
                JSONObject obj = new JSONObject();
                for (String phone : arr) {
                    if(StringUtil.isPresent(phone)) {
                        boolean isBlack = service.verify(phone);
                        obj.put(phone, isBlack?1:0);
                    }
                }
                result.setCode(0);
                result.setData(obj);
            }catch (Exception e){
                logger.error("verify failed!",e);
                result.setCode(-1);
                result.setMessage(e.getMessage());
            }
        }

        return result;
    }



}
