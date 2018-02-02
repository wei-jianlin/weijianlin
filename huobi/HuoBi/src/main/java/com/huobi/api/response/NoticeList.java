package com.huobi.api.response;

import java.util.List;

/**
 * <p>公告列表</p>
 * <p>功能详细描述</p>
 * @since 2018年1月25日
 * deprecated 如果已经不再推荐使用，请在标识符前加上@符号
 */

public class NoticeList {

    private int start;
    
    private int limt;
    
    private int totalCount;
    
    private int pages;
    
    private List<Notice> items;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimt() {
        return limt;
    }

    public void setLimt(int limt) {
        this.limt = limt;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<Notice> getItems() {
        return items;
    }

    public void setItems(List<Notice> items) {
        this.items = items;
    }
    
    
}
