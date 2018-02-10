package priv.weijianlin.huobi.model;

import priv.weijianlin.huobi.util.AccountInfo;

public class CreateOrderModel {
    
    public CreateOrderModel(){}
    
    public CreateOrderModel(String symbol,String price,String type){
        this.price = price;
        this.symbol = symbol;
        this.type = type;
    }
    
    /**
     * 账户ID，必填，例如："12345"
     */
    private String accountId = AccountInfo.SPOT_ACCOUNT_ID;
    
    /**
     * 当订单类型为buy-limit,sell-limit时，表示订单数量， 当订单类型为buy-market时，表示订单总金额， 当订单类型为sell-market时，表示订单总数量
     */
    private String amount;

    /**
     * 订单类型，取值范围"buy-market,sell-market,buy-limit,sell-limit"
     */
    private String type;
    
    /**
     * 订单价格，仅针对限价单有效，例如："1234.56"
     */
    private String price;;
    
    /**
     * 交易对，必填，例如："ethcny"，
     */
    private String symbol;

    /**
     * 订单来源，例如："api"
     */
    private String source = "api";

    
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    
    
}
