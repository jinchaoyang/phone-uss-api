package com.renhe.tenant.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 租户账单
 */
public class TenantBill implements Serializable {

    private String id;

    private String tenantId;
    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 计费类型
     */
    private String billType;

    /**
     * 费用单价
     */
    private long  fee;

    /**
     * 购买时长 / 次数
     */
    private long duration;

    /**
     * 总费用
     */
    private long amount;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 创建人
     */
    private String creatorId;

    /**
     * 交易ID
     */
    private String tradeId;

    /**
     * 备注
     */
    private String note;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }


    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
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

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
