package com.renhe.cdr.vo;

import com.renhe.utils.StringUtil;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
@Document(collection = "cc_vip_record")
public class VipRecordVo implements Serializable {
    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 租户IP
     */
    private String ip;


    /**
     * 通话ID
     */
    private String callId;

    /**
     * 验证号码
     */
    private String phone;

    /**
     * 校验结果
     */
    private Integer result;

    /**
     * 创建时间
     */
    private Long createTime;


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

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }


    public String getPhone() {
        String result = null;
        if(StringUtil.isPresent(this.phone)){
            int len =  this.phone.length();
            if(len>=11){
                result = this.phone.substring(0,3)+"****"+this.phone.substring(len-4,len);
            }else if(len>=5){
                result = this.phone.substring(0,2)+"****"+this.phone.substring(len-2,len);
            }else {
                result = "****";
            }
        }
        return result;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
