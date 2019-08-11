package com.renhe.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.renhe.base.Result;
import com.renhe.service.VerifyService;
import com.renhe.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping(value="/api/v1.0/black")
public class BlackListController {

    private static final Logger logger = LoggerFactory.getLogger(BlackListController.class);

    @Autowired
    VerifyService service;



    @PostMapping(value="/check")
    public JSONObject check(HttpServletRequest request, BufferedReader reader){
        String inputLine;
        String str = "";
        try {
        while ((inputLine = reader.readLine()) != null) {
            str += inputLine;
        }
            reader.close();
        } catch (IOException e) {
            logger.error("IOException",e);
        }

        JSONObject result = new JSONObject();
        if(StringUtil.isPresent(str)){
            JSONObject json = JSON.parseObject(str);
            String callId = json.getString("callId");
            String callee = json.getString("callee");

            if(StringUtil.isPresent(callee)) {
                callee = StringUtil.trim(callee);
                if(callee.length()>11){
                    callee = callee.substring(callee.length()-11);
                }
                boolean isBlack = service.verify(StringUtil.trim(callee));
                if(isBlack) {
                    result.put("forbid", 1);
                }
            }
            result.put("callId",callId);
        }

        logger.info("params -> {} ,result -> {}",str,result);
        return result;
    }



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
