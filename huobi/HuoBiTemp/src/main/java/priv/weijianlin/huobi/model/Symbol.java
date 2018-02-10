package priv.weijianlin.huobi.model;

import java.io.Serializable;

public class Symbol implements Serializable{

    private static final long serialVersionUID = 1788163974312627444L;
    
    private String baseCurrency;        //基础货币
    private String quoteCurrency;       //计价货币
    private String pricePrecision;      //价格精度
    private String amountPrecision;     //数量精度
    private String symbolPartition;     //交易区               main主区，innovation创新区，bifurcation分叉区

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

    public String getSymbolPartition() {
        return symbolPartition;
    }

    public void setSymbolPartition(String symbolPartition) {
        this.symbolPartition = symbolPartition;
    }
    
}
