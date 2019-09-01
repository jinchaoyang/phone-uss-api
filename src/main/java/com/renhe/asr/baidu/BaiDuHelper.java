package com.renhe.asr.baidu;

import com.alibaba.fastjson.JSONObject;

import com.renhe.utils.AudioUtil;
import com.renhe.utils.HttpHelper;
import com.renhe.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class BaiDuHelper {


    private static final Logger logger = LoggerFactory.getLogger(BaiDuHelper.class);

    private static final String REQUEST_ASR_URL = "http://vop.baidu.com/server_api";

    public static void asr(String token,String sourcePath,String targetPath,String cuid) throws IOException {

        byte[] bytes = Files.readAllBytes(Paths.get(sourcePath));
        AudioUtil.byteToWav(bytes,targetPath);
        byte[] bytes1 = Files.readAllBytes(Paths.get(targetPath));
        System.arraycopy(bytes,0,bytes1,0,2000);
        String content = Base64Utils.encodeToString(bytes1);
        JSONObject body = new JSONObject();
        body.put("speech",content);
        body.put("format","wav");
        body.put("rate",16000);
        body.put("channel", 1);
        body.put("cuid", cuid);
        body.put("token", token);
        body.put("len", bytes1.length);
        Map<String,String> headers = new HashMap<>();
        headers.put("content-type","application/json");
        String resp = HttpHelper.sendPost(REQUEST_ASR_URL,headers,body.toString());
        if(StringUtil.isPresent(resp)){
            logger.info("resp -> {}",resp);
        }

    }

}
