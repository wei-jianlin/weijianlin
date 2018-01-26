package com.huobi.api.response;

import java.util.List;

/**
 * <p>公告列表</p>
 * <p>功能详细描述</p>
 * @since 2018年1月25日
 * deprecated 如果已经不再推荐使用，请在标识符前加上@符号
 */

public class NoticeList {

    public int start;
    
    public int limt;
    
    public int totalCount;
    
    public int pages;
    
    public List<Notice> items;
}
