package com.renhe.asr.baidu;

import com.alibaba.fastjson.JSONObject;
import com.renhe.asr.properties.AsrProperties;
import com.renhe.utils.HttpHelper;
import com.renhe.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TokenUtil  {

    @Autowired
    AsrProperties asrProperties;

    /**
     * 获取token值
     * @param grantType
     * @param clientId
     * @param clientSecret
     */
    public  String getToken(String grantType,String clientId,String clientSecret){
         String baseUrl = asrProperties.getBaidu();
         String result = null;
         if(StringUtil.isPresent(baseUrl)){
             baseUrl = baseUrl+"?grant_type="+grantType+"&client_id="+clientId+"&client_secret="+clientSecret;
             try {
                result = HttpHelper.sendPost(baseUrl,(new JSONObject()).toString());
                if(StringUtil.isPresent(result)){
                    System.out.println(result);
                }
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         return result;
    }

}
