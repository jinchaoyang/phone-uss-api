package com.renhe.base;

import java.io.Serializable;

/**
 * 结果封装类
 * @param <T>
 */
public class Result<T> implements Serializable {
    /**
     * 响应状态码 0：成功 其它：失败
     */
    private int code;
    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应结果描述
     */
    private String message = "";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
