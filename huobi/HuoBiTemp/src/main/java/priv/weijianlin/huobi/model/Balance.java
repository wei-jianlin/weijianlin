package priv.weijianlin.huobi.model;

public class Balance {

    private String balance;      //余额
    
    private String currency;     //币种
    
    private String type;         //类型    trade: 交易余额，frozen: 冻结余额

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
