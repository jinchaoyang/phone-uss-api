package com.renhe.qc.entity;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 质检话单
 */
public class QcCdr implements Serializable {

    @Id
    private String id;


    private String host;

    /**
     * 录音文件名称
     */
    private String name;

    /**
     * 录音地址
     */
    private String url;

    /**
     * 被叫号码
     */
    private String callee;

    /**
     * 主叫网关IP
     */
    private String callerIp;

    /**
     * 被叫网关IP
     */
    private String calleeIp;

    /**
     * 呼叫时间
     */
    private String callAt;

    /**
     * 类型
     */
    private String type;

    /**
     * 厂商
     */
    private String profile;

    /**
     * 0: 未转换
     * 1：待质检
     * 2：已质检
     * -1: 质检失败
     */
    private int status;

    /**
     * 转换语音结果
     */
    private String result;

    /**
     * 质检敏感词匹配
     */
    private String tag;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 质检提交时间
     */
    private String createTime;

    /**
     * 质检结果时间
     */
    private String resultTime;

    /**
     * 质检失败原因
     */
    private String err;

    private int isHit;

    /**
     * 租户ID
     */
    private String tenantId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCallee() {
        return callee;
    }

    public void setCallee(String callee) {
        this.callee = callee;
    }

    public String getCallerIp() {
        return callerIp;
    }

    public void setCallerIp(String callerIp) {
        this.callerIp = callerIp;
    }

    public String getCalleeIp() {
        return calleeIp;
    }

    public void setCalleeIp(String calleeIp) {
        this.calleeIp = calleeIp;
    }

    public String getCallAt() {
        return callAt;
    }

    public void setCallAt(String callAt) {
        this.callAt = callAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getResultTime() {
        return resultTime;
    }

    public void setResultTime(String resultTime) {
        this.resultTime = resultTime;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getIsHit() {
        return isHit;
    }

    public void setIsHit(int isHit) {
        this.isHit = isHit;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}

