package com.renhe.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.renhe.service.VerifyService;
import com.renhe.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@RestController
@RequestMapping(value="/api/v1.0/vip")
public class VipController {


    private static final Logger logger = LoggerFactory.getLogger(BlackListController.class);

    @Autowired
    VerifyService service;



    @PostMapping(value="/check")
    public JSONObject check(HttpServletRequest request, BufferedReader reader){
        String inputLine;
        String str = "";
        String ip = getIpAddr(request);
        JSONObject result = new JSONObject();
        try {
            boolean valid = true;
            if(valid) {
                while ((inputLine = reader.readLine()) != null) {
                    str += inputLine;
                }
                reader.close();
            }else{
                result.put("forbid",1);
                result.put("message","No Permission");
            }
        } catch (IOException e) {
            logger.error("IOException",e);
        }
        logger.info("method -> {}, ip -> {}, params -> {} ,result -> {}","VIPBEGIN",ip,str,result);


        if(StringUtil.isPresent(str)){
           // String today = DateUtil.getToday();
            //service.count(ip,today);

            JSONObject json = JSON.parseObject(str);
            String callId = json.getString("callId");
            String callee = json.getString("callee");
            boolean isVip = false;

            if(StringUtil.isPresent(callee)) {
                callee = StringUtil.trim(callee);
                if(callee.length()>11){
                    callee = callee.substring(callee.length()-11);
                }
                isVip = service.verifyVip(StringUtil.trim(callee));
                if(!isVip) {
                    result.put("forbid",1);
                }
            }
            result.put("callId",callId);

        }

        logger.info("ip -> {}, params -> {} ,result -> {}",ip,str,result);
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


}
