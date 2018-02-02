package com.huobi.api.model;

public class SimpleSymbolByKlineModel implements Comparable<SimpleSymbolByKlineModel>{

    private String symbol;
    
    private int root;               //几根
    
    private String period;          //几分钟线
    
    private Double buyRise;         //跌幅买入
    
    private Double saleRise;        //涨幅卖出
    
    private Double totalProfit;     //总收益
    
    private int count;              //权重,交易次数

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Double getBuyRise() {
        return buyRise;
    }

    public void setBuyRise(Double buyRise) {
        this.buyRise = buyRise;
    }

    public Double getSaleRise() {
        return saleRise;
    }

    public void setSaleRise(Double saleRise) {
        this.saleRise = saleRise;
    }

    public Double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(Double totalProfit) {
        this.totalProfit = totalProfit;
    }
    
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * 先按权重排序,在按收益排序,buyRise,saleRise
     * */
    @Override
    public int compareTo(SimpleSymbolByKlineModel o) {
        int i = o.getCount() - this.count;
        if(i == 0){
            if(o.getTotalProfit() > this.totalProfit){
                i = 1;
            }else if(o.getTotalProfit() < this.totalProfit){
                i = -1;
            }else {
                if(o.getBuyRise() < this.buyRise){
                    i = 1;
                }else if(o.getBuyRise() > this.buyRise){
                    i = -1;
                }else{
                    if(o.getSaleRise() < this.saleRise){
                        i = 1;
                    }else if(o.getSaleRise() > this.saleRise){
                        i = -1;
                    }else{
                        i = 0;
                    }
                }
            }
        }
        return i;
    }
    
    
}
