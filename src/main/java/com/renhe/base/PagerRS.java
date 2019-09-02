package com.renhe.base;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

public class PagerRS<T> implements Serializable {

    private Pager pager;

    private List<T> result;

    public PagerRS() {
    }

    public PagerRS(Pager pager, List<T> result) {
        this.pager = pager;
        this.result = result;
    }

    public List<T> getResult() {
        return this.result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public Pager getPager() {
        return this.pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public Result<JSONObject> toResult() {
        JSONObject obj = new JSONObject();
        obj.put("totalPage", this.pager.getTotalPage());
        obj.put("totalRow", this.pager.getTotalRow());
        obj.put("data", this.result);
        Result<JSONObject> result = new Result();
        result.setCode(0);
        result.setData(obj);
        return result;
    }
}
