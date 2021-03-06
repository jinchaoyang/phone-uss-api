package com.renhe.auth.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.renhe.utils.StringUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {


    private static final String SECRET_KEY="jlskdfj1oiy@#$%^&&10923094733fdfdjla$%^sfds13dsf@#s";

    private static final String ISSUER_NAME="litbadboy@126.com";

    /**
     * JWT生成token
     * @param subject
     * @param ttlMills
     * @return
     */
    public  static String createToken(String subject, long ttlMills){
        long nowMills = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(nowMills+ttlMills))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY);
        return builder.compact();
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public static Claims parseToken(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }


    /**
     * 获取token中的subject信息
     * @param token
     * @return
     */
    public static JSONObject getSubjectInfo(String token){
        JSONObject result = new JSONObject();
        Claims claims = parseToken(token);
        if(null!=claims && StringUtil.isPresent(claims.getSubject())){
            result = JSON.parseObject(claims.getSubject());
        }
        return result;
    }

    /**
     * 获取当前登录人的ID信息
     * @param token
     * @return
     */
    public static String getUserId(String token){
        JSONObject subject = getSubjectInfo(token);
        if(subject.containsKey("id")) {
            return subject.getString("id");
        }
        return null;
    }


    /**
     * 获取当前登录用户的租户信息
     * @param token
     * @return
     */
    public static String getTenantId(String token){
        JSONObject subject = getSubjectInfo(token);
        if(subject.containsKey("tenantId")) {
            return subject.getString("tenantId");
        }
        return null;
    }




    public static void main(String[] args){

        Map<String,Object> params = new HashMap<>();
        params.put("id","jinchaoyang");
        params.put("tenantId","unicss");
        String subject = JSON.toJSONString(params);
        String token  = createToken(subject,1000*3600*24*7);
        Claims claims = parseToken(token);
        System.out.println("id==>"+claims.getId());
        System.out.println("issuedAt==>"+claims.getIssuedAt());
        System.out.println("subject==>"+claims.getSubject());
        System.out.println("expireation=>"+claims.getExpiration());

        System.out.println("Issuser=>"+claims.getIssuer());





        System.out.println(token);
    }











}
