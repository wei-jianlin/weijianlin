package com.huobi.api.response;

/**
 * <p>批量获取最近的交易记录Data</p>
 */
public class HistorytradeDetail {

    public Long id;                 //成交ID
    
    public Double amount;           //成交量
    
    public Double price;            //成交价
    
    public String direction;        //主动成交方向
    
    public Long ts;                 //成交时间
}
