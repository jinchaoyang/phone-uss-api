package com.renhe.tenant.entity;

import java.io.Serializable;

/**
 * 租户IP 信息
 */
public class TenantSetting implements Serializable {

    /**
     * 主键，同租户ID
     */
    private String id;

    /**
     * IP地址
     */
    private String ip;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
