package com.renhe.asr.baidu;

import com.renhe.base.Result;
import com.renhe.utils.AudioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(value="/asr/baidu")
public class BaiduController {

    @Autowired
    TokenUtil tokenUtil;



    @GetMapping(value="/token")
    public Result<String> getToken(){
        Result<String> result = new Result<>();
        String grantType = "client_credentials";
        String clientId = "hafjSLVZG2h6fSxWE8XZp7s8";
        String secretKey="KwvaL54FS15xxvLohPuuvXyIOhPjLwga";
        String resp = tokenUtil.getToken(grantType,clientId,secretKey);
        String cuid = UUID.randomUUID().toString().replace("-","");
        String sourcePath = "/Users/mac/Desktop/0621.mp3";
        String targetPath = "/tmp/11111.wav";
        try {
            BaiDuHelper.asr(resp,sourcePath,targetPath,cuid);
        } catch (IOException e) {
            e.printStackTrace();
        }

        result.setCode(0);
        return result;
    }
}
