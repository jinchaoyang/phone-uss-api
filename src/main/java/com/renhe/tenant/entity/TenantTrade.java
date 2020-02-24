package com.renhe.tenant.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 账户交易日志
 */
public class TenantTrade implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 交易类型 1：充值 2：消费 3：冲账
     */
    private String tradeType;

    /**
     * 交易金额，单位分
     */
    private long amount;

    /**
     * 支付方式 支付宝: alipay 微信：wx
     */
    private String payType;

    /**
     * 备注
     */
    private String note;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 创建人
     */
    private String creatorId;


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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
