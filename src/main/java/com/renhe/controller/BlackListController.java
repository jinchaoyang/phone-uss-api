package com.renhe.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.renhe.base.Result;
import com.renhe.service.VerifyService;
import com.renhe.utils.DateUtil;
import com.renhe.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
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
        String ip = getIpAddr(request);
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
            String today = DateUtil.getToday();
            service.count(ip,today);

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
                   service.countBlack(ip,today);
                }
            }
            result.put("callId",callId);

        }

        logger.info("ip -> {}, params -> {} ,result -> {}",ip,str,result);
        return result;
    }



    /**
     * 单个号码验证是否为黑名单
     * @param phone
     * @return
     */
    @CrossOrigin
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
    @CrossOrigin
    @GetMapping(value="/batchVerify")
    public Result<JSONObject> batchVerify(@RequestParam("phones")String phones){
        Result<JSONObject> result = new Result<>();
        if(!StringUtil.isPresent(phones)){
            result.setMessage("必要参数不能为空");
            result.setCode(-1);
        }else{
            try {
                phones = phones.replace("\n",",");
                String[] arr = phones.split(",");
                JSONObject obj = new JSONObject(true);
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


    private  String getIpAddr(HttpServletRequest request) {
        String ip;
        int index;
        try {
            ip = request.getHeader("x-forwarded-for");
            // Proxy-Client-IP 这个一般是经过apache http服务器的请求才会有，用apache http做代理时一般会加上Proxy-Client-IP请求头，而WL-Proxy-Client-IP是他的weblogic插件加上的头。
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if(StringUtil.isEmpty(ip)){
                return "";
            }
            index = ip.indexOf(",");

            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        } catch (Exception e) {
            return "";
        }
    }


    @CrossOrigin
    @PostMapping(value="/upload")
    public String upload(MultipartFile uploadFile){
        JSONObject obj = new JSONObject();
        int rcode = 0;
        String message = null;
        File destFile = new File("/tmp/"+uploadFile.getOriginalFilename());
        try {
            uploadFile.transferTo(destFile);
            if(destFile.exists()) {
               long count =  service.uploadBlackRecord(destFile);
                obj.put("data",count);
            }
        } catch (IOException e) {
            e.printStackTrace();
            rcode = -1;
            message = e.getMessage();
        }finally{
            destFile.delete();
            obj.put("rcode",rcode);
            obj.put("message",message);
            logger.info("[upload]: fileName -> {}, result -> {}",uploadFile.getOriginalFilename(),JSON.toJSONString(obj));
            return obj.toString();
        }
    }


}
