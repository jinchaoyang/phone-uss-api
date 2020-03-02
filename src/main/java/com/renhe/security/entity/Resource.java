package com.renhe.security.entity;

import java.io.Serializable;

/**
 * 权限菜单管理
 */
public class Resource implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源编码
     */
    private String code;

    /**
     * 资源类型 MENU:菜单 ACTION:操作
     */
    private String type;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     *  是否可见
     */
    private int visible;

    /**
     * 父亲节点
     *
     */
    private String parentId;

    /**
     * 排序
     */
    private int seq;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
