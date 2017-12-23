package com.huobi.api.util;

/**
 * <p>相关计算公式</p>
 */
public class Arithmetic {

    public static void main(String[] args) {
        System.out.println(riseAndFall(10994,12355));
    }

    /** 
     * <p>实际幅度公式:</p>
     * <p>b * (1 + a) + a</p>
     * a:计价货币幅度,b:基础货币幅度
     */
    public static double reRange(double a,double b){
        return b * (1 + a) + a;
    }
    
    /** 
     * <p>涨跌幅</p>
     * @param sourcePrice  开始价
     * @param currentPrice 结束价
     * @return
     */
    public static double riseAndFall(double sourcePrice,double currentPrice){
        return (currentPrice - sourcePrice) / sourcePrice;
    }
}
