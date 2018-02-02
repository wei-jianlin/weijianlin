package com.huobi.api.util;

/**
 * <p>常量</p>
 * <p>功能详细描述</p>
 * @author cz
 * @version V1.0, 2017年12月21日
 * @see 包名.类名#类名
 * @since 2017年12月21日
 */
public class Constant {

    //30分钟线跌幅买入
    public static final double BUY_RISE1_30 = -0.067;
    //30分钟线涨幅卖出
    public static final double SALE_RISE1_30 = 0.027;
    
    //费率
    public static final double RATE = 0.002 * 2.5;
    
    /**
     * 限价买入
     */
    public static final String BUY_LIMIT = "buy-limit";
    /**
     * 限价卖出
     */
    public static final String SELL_LIMIT = "sell-limit";
    /**
     * 市价买入
     */
    public static final String BUY_MARKET = "buy-market";
    /**
     * 市价卖出
     */
    public static final String SELL_MARKET = "sell-market";
    
    public static final String MIN_1 = "1min";
    
    public static final String MIN_5 = "5min";
    
    public static final String MIN_15 = "15min";
    
    public static final String MIN_30 = "30min";
    
    public static final String MIN_60 = "60min";
    
    public static final String DAY_1 = "1day";
    
    public static final String MON_1 = "1mon";
    
    public static final String WEEK_1 = "1week";
    
    public static final String YEAR_1 = "1year";
    
}
