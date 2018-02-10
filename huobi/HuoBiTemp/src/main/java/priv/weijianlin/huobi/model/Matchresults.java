package priv.weijianlin.huobi.model;

public class Matchresults {

    private Long createdAt;          //成交时间
    
    private String filledAmount;     //成交数量
    
    private String filledFees;       //成交手续费
    
    private Long id;                 //订单成交记录ID
    
    private Long matchId;            //撮合ID
    
    private Long orderId;            //订单 ID
    
    private String price;            //成交价格
    
    private String source;           //订单来源
    
    private String symbol;           //交易对
    
    private String type;     //buy-market：市价买, sell-market：市价卖, buy-limit：限价买, sell-limit：限价卖

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getFilledAmount() {
        return filledAmount;
    }

    public void setFilledAmount(String filledAmount) {
        this.filledAmount = filledAmount;
    }

    public String getFilledFees() {
        return filledFees;
    }

    public void setFilledFees(String filledFees) {
        this.filledFees = filledFees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
