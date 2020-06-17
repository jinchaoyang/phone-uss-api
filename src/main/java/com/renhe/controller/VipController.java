package com.renhe.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.renhe.base.Result;
import com.renhe.callable.TaskManager;
import com.renhe.callable.VipFilterTask;
import com.renhe.configuration.CustomerProperties;
import com.renhe.service.FileUploadService;
import com.renhe.service.VerifyService;
import com.renhe.utils.DateUtil;
import com.renhe.utils.FileUtil;
import com.renhe.utils.IDUtil;
import com.renhe.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping(value="/api/v1.0/vip")
public class VipController {


    private static final Logger logger = LoggerFactory.getLogger(BlackListController.class);

    @Autowired
    VerifyService service;

    @Autowired
    CustomerProperties properties;

    @Autowired
    FileUploadService uploadService;



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
                result.put("message","No Resource");
            }
        } catch (IOException e) {
            logger.error("IOException",e);
        }
        logger.info("method -> {}, ip -> {}, params -> {} ,result -> {}","VIPBEGIN",ip,str,result);


        if(StringUtil.isPresent(str)){
            String today = DateUtil.getToday();
            service.statVip(ip,today);

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
                }else{
                    service.countVip(ip,today);
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


    @CrossOrigin
    @PostMapping(value="/filter")
    public Result<JSONObject> filteFile(MultipartFile uploadFile){
        Result<JSONObject> result = new Result<>();
        JSONObject obj = new JSONObject();
        int rcode = 0;
        String message = null;
        File destFile = new File("/tmp/"+uploadFile.getOriginalFilename());
        try {
            uploadFile.transferTo(destFile);
            if(destFile.exists()) {
                long totalLines = FileUtil.getLineNumbers(destFile);
                String taskId = IDUtil.generate();
                TaskManager.setSnapshot(taskId,totalLines);
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                VipFilterTask task = new VipFilterTask(destFile,taskId);
                executorService.submit(task);
                executorService.shutdown();
                obj.put("taskId",taskId);
                obj.put("total",totalLines);
                result.setData(obj);
            }else{
                rcode = -1;
                message = "file not exists!";
            }
        } catch (IOException e) {
            e.printStackTrace();
            rcode = -1;
            message = e.getMessage();
        }finally{
            result.setCode(rcode);
            result.setMessage(message);
            logger.info("[filter]: fileName -> {}, result -> {}",uploadFile.getOriginalFilename(),JSON.toJSONString(obj));

        }
        return result;
    }


    @CrossOrigin
    @GetMapping(value="/progress/{taskId}")
    public Result<JSONObject> filteProgress(@PathVariable("taskId")String taskId){
        Result<JSONObject> result = new Result<>();
        JSONObject obj = new JSONObject();
        int rcode = 0;
        String message = null;

        try {
            long total = TaskManager.getSnapshot(taskId);
            long complete = TaskManager.getProgress(taskId);
            obj.put("total",total);
            obj.put("complete",complete);
            result.setData(obj);
        } catch (Exception e) {
            e.printStackTrace();
            rcode = -1;
            message = e.getMessage();
            logger.error("[filterProgess]: taskId -> {}",taskId,e);

        }
        result.setCode(rcode);
        result.setMessage(message);
        return result;
    }

    @RequestMapping(value="/download",method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(@RequestParam("taskId") String taskId){
        File f = new File(properties.getDestPath()+"/"+taskId+".txt");
        ResponseEntity<byte[]> result = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", taskId+".txt");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        if(f.exists()){
            byte[] content = uploadService.getBytes(f.getAbsolutePath());
            result = new ResponseEntity<byte[]>(content, headers, HttpStatus.CREATED);
        }else{
            result = new ResponseEntity<byte[]>(new byte[0], headers, HttpStatus.NO_CONTENT);
        }
        return result;
    }




}
