package priv.weijianlin.huobi.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>相关计算公式</p>
 */
public class Arithmetic {

    public static void main(String[] args) {
        System.out.println(new BigDecimal("0.00").compareTo(new BigDecimal("0")));
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
     * @param currentPrice      当前成交价
     * @param referencePrice    参考价
     * @return
     */
    public static double riseAndFall(double currentPrice,double referencePrice){
        return (currentPrice - referencePrice) / referencePrice;
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
        if(num == null) return null;
        return new BigDecimal(num).setScale(Integer.valueOf(precision), BigDecimal.ROUND_DOWN).toString();
    }
    
    /** 
     * <p>涨跌排名</p>
     * <p>功能详细描述</p>
     * @param map 交易对,涨跌幅
     * @param riseList 涨跌幅List
     * @param size 前几名
     * @param sort asc 跌,desc 涨 ,默认涨
     */
    public static void ranking(Map<String,Double> map,List<Double> riseList,int size,String sort){
        riseList.sort((Double a,Double b) -> {
            if("asc".equals(sort)){
                return a.compareTo(b);
            }else if("desc".equals(sort)){
                return b.compareTo(a);
            }else{
                return b.compareTo(a);
            }
        });
        Set<String> keys = map.keySet();
        for(int i = 0; i < size * 3; i++){
            for(String key : keys){
                Double rise = map.get(key);
                if(rise.compareTo(riseList.get(i)) == 0){
                    System.out.println("排名:" + key + "涨幅:" + rise * 100 + "%");
                }
            }
        }
    }
}
