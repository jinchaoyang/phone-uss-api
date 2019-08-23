package com.renhe.asr.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Asr配置文件
 */
@ConfigurationProperties(prefix = "asr")
@Component
public class AsrProperties implements Serializable {

    private String baidu;

    private String alibaba;

    private String iflytek;


    public String getBaidu() {
        return baidu;
    }

    public void setBaidu(String baidu) {
        this.baidu = baidu;
    }

    public String getAlibaba() {
        return alibaba;
    }

    public void setAlibaba(String alibaba) {
        this.alibaba = alibaba;
    }

    public String getIflytek() {
        return iflytek;
    }

    public void setIflytek(String iflytek) {
        this.iflytek = iflytek;
    }
}
