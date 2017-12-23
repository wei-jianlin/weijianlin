package com.huobi.api;

/**
 * <p>笔记心得</p>
 * <p>USDT与美元1:1挂钩,美元与人民币有汇率
 * USDT可以买BTC,BTC可以买其他加密货币.USDT是BTC的计价币种,BTC是其他加密货币的计价币种.
 * 那么以BTC为计价币种的货币价值会随着BTC浮动.
 * </p>
 */
public class Note {

    public static void main(String[] args) {
        //test();
        System.out.println(range(-0.1186,0.14) * 100 + "%");
    }

    static double range(double a,double b){
        return b * (1 + a) + a;
    }
    
    /** 
     * <p>实际幅度公式:</p>
     * <p>b * (1 + a) + a</p>
     * b:基础货币幅度,a:计价货币幅度
     */
    static void test(){
        //假设开始拥有10个ltc,那么我们的美元资产有
        double temp = 0;
        final double LTC_NUM = 10;
        double usd = LTC_NUM * LTC_BTC * BTC_USDT;
        //实际幅度公式:b * (1 + a) + a
        System.out.println("刚开始持有的美元资产：" + usd + "$");
        //btc涨3%,ltc不动,那么资产实际涨了3%
        System.out.print("btc涨5%,ltc不动,资产实际涨了:");
        temp = LTC_NUM * LTC_BTC * BTC_USDT * (1 + (+0.03));
        System.out.print((temp - usd) / usd * 100 + "%,");
        System.out.println("现有美元资产:" + temp + "$;");
        //验证
        System.out.println("验证,实际幅度:" + usd * (0 * (1 + 0.03) + 0.03) + "$;");
        //ltc涨5%,btc不动,那么资产实际涨了5%
        System.out.print("ltc涨5%,btc不动,资产实际涨了:");
        temp = LTC_NUM * (LTC_BTC * (1 + (+0.05)))* BTC_USDT;
        System.out.print((temp - usd) / usd * 100 + "%,");
        System.out.println("现有美元资产:" + temp + "$;");
        //验证
        System.out.println("验证,实际幅度:" + usd * (0.05 * (1 + 0) + 0) + "$;");
        //btc涨3%,ltc涨5%,那么资产实际涨了8.15%
        System.out.print("ltc涨5%,btc不动,资产实际涨了:");
        temp = LTC_NUM * (LTC_BTC * (1 + (+0.05)))* BTC_USDT * (1 + (+0.03));
        System.out.print((temp - usd) / usd * 100 + "%,");
        System.out.println("现有美元资产:" + temp + "$;");
        //验证
        System.out.println("验证,实际幅度:" + usd * (0.05 * (1 + 0.03) + 0.03) + "$;");
        //btc涨3%,ltc涨-5%
        System.out.print("ltc涨5%,btc不动,资产实际涨了:");
        temp = LTC_NUM * (LTC_BTC * (1 + (-0.05)))* BTC_USDT * (1 + (+0.03));
        System.out.print((temp - usd) / usd * 100 + "%,");
        System.out.println("现有美元资产:" + temp + "$;");
        //验证
        System.out.println("验证,实际幅度:" + usd * (-0.05 * (1 + 0.03) + 0.03) + "$;");
        //btc涨-3%,ltc涨-5%
        System.out.print("ltc涨5%,btc不动,资产实际涨了:");
        temp = LTC_NUM * (LTC_BTC * (1 + (-0.05)))* BTC_USDT * (1 + (-0.03));
        System.out.print((temp - usd) / usd * 100 + "%,");
        System.out.println("现有美元资产:" + temp + "$;");
        //验证
        System.out.println("验证,实际幅度:" + usd * (-0.05 * (1 + (-0.03)) + (-0.03)) + "$;");
    }
    
    //1BTC == 1000美元
    static final double BTC_USDT = 1000;
    //1LTC == 0.6BTC
    static final double LTC_BTC = 0.6;
}
