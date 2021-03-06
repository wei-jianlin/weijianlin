package priv.weijianlin.huobi.model;

import java.io.Serializable;

public class SimpleSymbolByKlineModel implements Serializable,Comparable<SimpleSymbolByKlineModel>{

    private static final long serialVersionUID = 6143044227910278554L;

    private Symbol symbol;
    
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

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    /**
     * 风险低的排在前面,低权重,低buyRise,高saleRise,高收益
     * */
    @Override
    public int compareTo(SimpleSymbolByKlineModel o) {
        int i = this.getCount() - o.getCount();
        if(i == 0){
            if(this.getBuyRise() > o.getBuyRise()){
                i = 1;
            }else if(this.getBuyRise() < o.getBuyRise()){
                i = -1;
            }else{
                if(this.getSaleRise() > o.getSaleRise()){
                    i = -1;
                }else if(this.getSaleRise() < o.getSaleRise()){
                    i = 1;
                }else{
                    if(this.getTotalProfit() > o.getTotalProfit()){
                        i = 1;
                    }else if(this.getTotalProfit() < o.getTotalProfit()){
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
