package priv.weijianlin.huobi.model;

public class OrderDetails {

    private Long accountId;              //账户 ID
    
    private String amount;               //订单数量
    
    private Long canceledAt;             //订单撤销时间
    
    private Long createdAt;              //订单创建时间
    
    private String fieldAmount;          //已成交数量
    
    private String fieldCashAmoun;       //已成交总金额
    
    private String fieldFees;            //已成交手续费（买入为币，卖出为钱）
    
    private Long finishedAt;             //最后成交时间
    
    private Long id;                     //订单ID
    
    private String price;                //订单价格
    
    private String source;               //订单来源
    
    /* pre-submitted 准备提交,    
     * submitting , submitted 已提交,
     * partial-filled 部分成交,
     * partial-canceled 部分成交撤销,
     * filled 完全成交, 
     * canceled 已撤销
     * */ 
    private String state;                //订单状态
    
    private String symbol;               //交易对
    
    /* buy-market：市价买, 
     * sell-market：市价卖, 
     * buy-limit：限价买, 
     * sell-limit：限价卖
     * */
    private String type;                 //订单类型

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Long getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(Long canceledAt) {
        this.canceledAt = canceledAt;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getFieldAmount() {
        return fieldAmount;
    }

    public void setFieldAmount(String fieldAmount) {
        this.fieldAmount = fieldAmount;
    }

    public String getFieldCashAmoun() {
        return fieldCashAmoun;
    }

    public void setFieldCashAmoun(String fieldCashAmoun) {
        this.fieldCashAmoun = fieldCashAmoun;
    }

    public String getFieldFees() {
        return fieldFees;
    }

    public void setFieldFees(String fieldFees) {
        this.fieldFees = fieldFees;
    }

    public Long getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Long finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
