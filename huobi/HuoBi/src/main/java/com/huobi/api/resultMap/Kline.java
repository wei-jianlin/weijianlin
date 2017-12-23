package com.huobi.api.resultMap;

public class Kline {

    public String id;           //K线id
    public String amount;       //成交量  
    public String count;        //成交笔数
    public String open;         //开盘价
    public String close;        //收盘价,当K线为最晚的一根时，是最新成交价
    public String low;          //最低价
    public String high;         //最高价
    public String vol;          //成交额, 即 sum(每一笔成交价 * 该笔的成交量)
}
