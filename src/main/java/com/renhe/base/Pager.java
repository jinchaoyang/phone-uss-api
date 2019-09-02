package com.renhe.base;

import java.io.Serializable;

public class Pager implements Serializable {

    private int pageNo;
    private int pageSize;
    private long totalRow;
    private int totalPage;

    public Pager() {
    }

    public Pager(int pageNo, int pageSize, long totalRow) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalRow = totalRow;
        this.totalPage = (int)((totalRow + (long)pageSize - 1L) / (long)pageSize);
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalRow() {
        return this.totalRow;
    }

    public void setTotalRow(long totalRow) {
        this.totalRow = totalRow;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
