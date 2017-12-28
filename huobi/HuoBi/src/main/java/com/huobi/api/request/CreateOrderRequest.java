package com.huobi.api.request;

import com.huobi.api.util.AccountInfo;

public class CreateOrderRequest {
    
    public CreateOrderRequest(){}
    
    public CreateOrderRequest(String symbol,String price,String type){
        this.price = price;
        this.symbol = symbol;
        this.type = type;
    }
    
    /**
     * 账户ID，必填，例如："12345"
     */
    public String accountId = AccountInfo.SPOT_ACCOUNT_ID;
    
    /**
     * 当订单类型为buy-limit,sell-limit时，表示订单数量， 当订单类型为buy-market时，表示订单总金额， 当订单类型为sell-market时，表示订单总数量
     */
    public String amount;

    /**
     * 订单类型，取值范围"buy-market,sell-market,buy-limit,sell-limit"
     */
    public String type;
    
    /**
     * 订单价格，仅针对限价单有效，例如："1234.56"
     */
    public String price;;
    
    /**
     * 交易对，必填，例如："ethcny"，
     */
    public String symbol;

    /**
     * 订单来源，例如："api"
     */
    public String source = "api";
    
}
