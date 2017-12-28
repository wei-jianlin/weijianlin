package com.huobi.api.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <p>相关计算公式</p>
 */
public class Arithmetic {

    public static void main(String[] args) {
        System.out.println(new BigDecimal("0.00").compareTo(new BigDecimal("0")));
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
     * <p>百分比涨跌幅</p>
     * @param sourcePrice  开始价
     * @param currentPrice 结束价
     * @return
     */
    public static String riseAndFallToString(double riseAndFall){  
        String temp = String.valueOf(riseAndFall * 100);
        int spotIndex = temp.indexOf(".") != -1 ? temp.indexOf(".") : temp.length();
        int subLength = temp.length() < 4 + spotIndex ? temp.length() : 4 + spotIndex;
        return temp.substring(0, subLength) + "%";
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
    
    /** 
     * <p>除法</p>
     * <p>3位小数,多余取0</p>
     * @param dividend 被除数
     * @param divisor  除数 
     * @param precision  精度 
     * @return 结果的字符串
     */
    public static String divide(String dividend,String divisor,String precision){
        if(new BigDecimal("0.00").compareTo(new BigDecimal(dividend)) == 0 ||
                new BigDecimal("0.00").compareTo(new BigDecimal(divisor)) == 0){
            return "0";
        }
        return new BigDecimal(dividend).divide(new BigDecimal(divisor),
                Integer.valueOf(precision),RoundingMode.DOWN).toString();
    }
    
    /** 
     * <p>精度控制</p>
     * <p>删除多余的小数位数</p>
     * @param num 
     * @param precision
     * @return
     */
    public static String setScale(String num,String precision){
        return new BigDecimal(num).setScale(Integer.valueOf(precision), BigDecimal.ROUND_DOWN).toString();
    }
}
