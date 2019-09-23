package com.renhe.tenant.entity;

import java.io.Serializable;

/**
 * 租户IP 信息
 */
public class TenantSetting implements Serializable {

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * IP地址
     */
    private String ip;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
