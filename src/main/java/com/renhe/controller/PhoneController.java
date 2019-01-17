package com.renhe.controller;

import com.alibaba.fastjson.JSONObject;
import com.renhe.configuration.CustomerProperties;
import com.renhe.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by jinchaoyang on 2018/12/18.
 */
@RestController
@RequestMapping(value="/phone")
public class PhoneController {

    @Autowired
    FileUploadService uploadService;

    @Autowired
    CustomerProperties properties;




    @RequestMapping(value="/upload",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    public String upload(MultipartFile uploadFile, @RequestParam("areaCode") String areaCode){
        JSONObject obj = new JSONObject();
        int rcode = 0;
        String message = null;
        File destFile = new File("/tmp/"+uploadFile.getOriginalFilename());
        File targetFile  = null;
        try {
            uploadFile.transferTo(destFile);
            if(destFile.exists()) {
                targetFile = uploadService.filtePhone(destFile, areaCode);
                obj.put("data",targetFile.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
            rcode = -1;
            message = e.getMessage();
        }finally{
            destFile.delete();
            obj.put("rcode",rcode);
            obj.put("message",message);
            return obj.toString();
        }
    }


    @RequestMapping(value="/download",method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(@RequestParam("file") String fileName){
        File f = new File(properties.getDestPath()+"/"+fileName);
        ResponseEntity<byte[]> result = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
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
