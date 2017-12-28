package com.huobi.api.response;

public class OrderDetails {

    public Long accountId;              //账户 ID
    
    public String amount;               //订单数量
    
    public Long canceledAt;             //订单撤销时间
    
    public Long createdAt;              //订单创建时间
    
    public String fieldAmount;          //已成交数量
    
    public String fieldCashAmoun;       //已成交总金额
    
    public String fieldFees;            //已成交手续费（买入为币，卖出为钱）
    
    public Long finishedAt;             //最后成交时间
    
    public Long id;                     //订单ID
    
    public String price;                //订单价格
    
    public String source;               //订单来源
    
    /* pre-submitted 准备提交,    
     * submitting , submitted 已提交,
     * partial-filled 部分成交,
     * partial-canceled 部分成交撤销,
     * filled 完全成交, 
     * canceled 已撤销
     * */ 
    public String state;                //订单状态
    
    public String symbol;               //交易对
    
    /* buy-market：市价买, 
     * sell-market：市价卖, 
     * buy-limit：限价买, 
     * sell-limit：限价卖
     * */
    public String type;                 //订单类型
}
