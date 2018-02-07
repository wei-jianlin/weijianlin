package priv.weijianlin.huobi.model;

public class Symbol {

    private String baseCurrency;        //基础货币
    private String quoteCurrency;       //计价货币
    private String symbol;              //交易对
    private String pricePrecision;      //价格精度
    private String amountPrecision;     //数量精度

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPricePrecision() {
        return pricePrecision;
    }

    public void setPricePrecision(String pricePrecision) {
        this.pricePrecision = pricePrecision;
    }

    public String getAmountPrecision() {
        return amountPrecision;
    }
    
    public void setAmountPrecision(String amountPrecision) {
        this.amountPrecision = amountPrecision;
    }
    
    
}
