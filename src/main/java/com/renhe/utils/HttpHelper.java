package com.renhe.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpHelper {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 发送POST请求
     * @param url
     * @param body
     * @return
     * @throws IOException
     */
    public static String sendPost(String url,String body) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = RequestBody.create(JSON, body);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().string();
        }else{
            throw new IOException("Unexpected code "+ response);
        }
    }



    /**
     * 发送POST请求
     * @param url
     * @param body
     * @return
     * @throws IOException
     */
    public static String sendPost(String url, Map<String,String> headers, String body) throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = RequestBody.create(JSON,StringUtil.trim(body));
        Request request = new Request.Builder().headers(Headers.of(headers)).url(url).post(requestBody).build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().string();
        }else if(response.code()==401 || response.code()==403){
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("message","Unauthorized");
            return JSONObject.toJSONString(map);
        }else{
            throw new IOException("Unexpected code "+ response);
        }
    }


    /**
     * 发送Delete请求
     * @param url
     * @param body
     * @return
     * @throws IOException
     */
    public static String sendDelete(String url,String body) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = RequestBody.create(JSON,body);
        Request request = new Request.Builder().url(url).delete(requestBody).build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().string();
        }else{
            throw new IOException("Unexpected code "+ response);
        }
    }




    /**
     * 发送Get请求
     * @param url
     * @return
     * @throws IOException
     */
    public static String sendGet(String url,Map<String,String> headers) throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .build();
        Request.Builder builder = new Request.Builder();
        headers.entrySet().stream().forEach(e -> builder.addHeader(e.getKey(),e.getValue()));
        Request request = builder.url(url).get().build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().string();
        }else if(response.code()==401 || response.code()==403){
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("message","Unauthorized");
            return JSONObject.toJSONString(map);
        }else{
            throw new IOException("Unexpected code "+ response);
        }
    }




    /**
     * 发送Get请求
     * @param url
     * @return
     * @throws IOException
     */
    public static String sendGet(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url(url).get().build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().string();
        }else{
            throw new IOException("Unexpected code "+ response);
        }
    }


    /**
     * 发送POST请求
     * @param url
     * @param body
     * @return
     * @throws IOException
     */
    public static String sendPost(String url,String authorization,String body) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .build();
        RequestBody requestBody = RequestBody.create(JSON,body);
        Request request = new Request.Builder().header("Authorization",authorization).url(url).post(requestBody).build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().string();
        }else{
            throw new IOException("Unexpected code "+ response);
        }
    }


    public static String sendFile(String url, String params, File file) throws IOException {

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(3,TimeUnit.MINUTES)
                .build();
        RequestBody jsonBody = RequestBody.create(JSON,params);
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
        RequestBody requestBody = new MultipartBody.Builder().addFormDataPart("uploadFile",file.getName(),fileBody).addFormDataPart("waterMark","0").build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().string();
        }else{
            throw new IOException("Unexpected code "+ response);
        }
    }
}
