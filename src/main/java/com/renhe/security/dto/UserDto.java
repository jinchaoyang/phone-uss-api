package com.renhe.security.dto;

import java.io.Serializable;

/**
 * 用户Dto
 */
public class UserDto implements Serializable {

    private String userName;

    private String password;

    private String tenantCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }
}
