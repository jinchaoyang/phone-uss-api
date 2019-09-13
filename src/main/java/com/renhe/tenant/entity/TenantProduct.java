package com.renhe.tenant.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 租户订购的服务
 */
public class TenantProduct implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 产品类型
     * 1001：黑名单
     * 1002: 录音质检
     */
    private String productType;

    /**
     * 计费类型
     * 1：包月
     * 2：按使用量
     */
    private String feeType;

    /**
     * 费率,单位分
     */
    private Integer fee;

    /**
     * 过期时间
     */
    private String expireAt;

    /**
     * 创建人
     */
    private String creatorId;

    /**
     * 最近操作人
     */
    private String operatorId;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(String expireAt) {
        this.expireAt = expireAt;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }
}
