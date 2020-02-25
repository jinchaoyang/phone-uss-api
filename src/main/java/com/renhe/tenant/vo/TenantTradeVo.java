package com.renhe.tenant.vo;

import java.io.Serializable;

public class TenantTradeVo implements Serializable {
    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 交易类型
     */
    private String tradeType;


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}
