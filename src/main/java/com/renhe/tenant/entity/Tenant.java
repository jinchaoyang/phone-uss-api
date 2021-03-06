package com.renhe.tenant.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 租户对象
 */
public class Tenant implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 租户名称
     */
    private String name;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人手机
     */
    private String contactPhone;

    /**
     * 联系人邮箱
     */
    private String email;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 创建人
     */
    private String creatorId;

    /**
     * 最近操作人
     */
    private String operatorId;

    /**
     * 状态
     * 0: 已删除 1：可用 2：禁用 3：欠费
     */
    private String status;

    private String ip;

    /**
     * 服务分组 1：内网 2：外网
     */
    private String tenantType;

    /**
     * 账户余额
     */
    private Long balance;

    /**
     * 透支金额
     */
    private Long overdraft;

    /**
     * 实时账户余额
     */
    private Long rtFee;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTenantType() {
        return tenantType;
    }

    public void setTenantType(String tenantType) {
        this.tenantType = tenantType;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(Long overdraft) {
        this.overdraft = overdraft;
    }

    public Long getRtFee() {
        return rtFee;
    }

    public void setRtFee(Long rtFee) {
        this.rtFee = rtFee;
    }
}
