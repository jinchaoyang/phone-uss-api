package com.renhe.tenant.entity;

import org.springframework.data.annotation.Transient;

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
     * 1002: 白名单
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
     * 生效时间
     */
    private String effectAt;

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

    /**
     * 厂商
     * 1：baidu
     * 2: 科大讯飞
     * 3: ali
     */
    private String vendor;

    /**
     * 状态
     * 0：禁用
     * 1：启用
     */
    private String status;

    /**
     * 账户ID
     */
    private String appId;

    /**
     * 密钥
     */
    private String appKey;



    @Transient
    private int duration;

    @Transient
    private String feeDesc;


    @Transient
    private String dissipationDesc;

    /**
     * 最低消费
     */
    private Long dissipation;

    /**
     * 下个扣费日期
     */
    private String costDate;


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

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getEffectAt() {
        return effectAt;
    }

    public void setEffectAt(String effectAt) {
        this.effectAt = effectAt;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getFeeDesc() {
        return feeDesc;
    }

    public void setFeeDesc(String feeDesc) {
        this.feeDesc = feeDesc;
    }

    public Long getDissipation() {
        return dissipation;
    }

    public void setDissipation(Long dissipation) {
        this.dissipation = dissipation;
    }

    public String getCostDate() {
        return costDate;
    }

    public void setCostDate(String costDate) {
        this.costDate = costDate;
    }

    public String getDissipationDesc() {
        return dissipationDesc;
    }

    public void setDissipationDesc(String dissipationDesc) {
        this.dissipationDesc = dissipationDesc;
    }
}

