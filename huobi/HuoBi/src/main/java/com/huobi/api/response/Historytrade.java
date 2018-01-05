package com.huobi.api.response;

import java.util.List;

/**
 * <p>批量获取最近的交易记录</p>
 * <p>功能详细描述</p>
 */
public class Historytrade {

    public Long id;                         //成交ID
        
    public Long ts;                         //成交时间
    
    public List<HistorytradeDetail> data;   //成交记录
}
