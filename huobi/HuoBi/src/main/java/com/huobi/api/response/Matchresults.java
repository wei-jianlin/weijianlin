package com.huobi.api.response;

public class Matchresults {

    public Long createdAt;          //成交时间
    
    public String filledAmount;     //成交数量
    
    public String filledFees;       //成交手续费
    
    public Long id;                 //订单成交记录ID
    
    public Long matchId;            //撮合ID
    
    public Long orderId;            //订单 ID
    
    public String price;            //成交价格
    
    public String source;           //订单来源
    
    public String symbol;           //交易对
    
    public String type;     //buy-market：市价买, sell-market：市价卖, buy-limit：限价买, sell-limit：限价卖
}
