package com.renhe.security.vo;


import java.io.Serializable;

public class UserVo implements Serializable {

    private String name;

    private String userName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
