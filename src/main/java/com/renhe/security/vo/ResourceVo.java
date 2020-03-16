package com.renhe.security.vo;

import java.io.Serializable;

public class ResourceVo implements Serializable {

    private String type;

    /**
     * 0: root节点  1：1级节点 2：2级节点
     */
    private Integer level;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
