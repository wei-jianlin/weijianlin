package com.huobi.api.resultMap;

public class Kline {

    public String id;           //K线id
    public Double amount;       //成交量  
    public Double count;        //成交笔数
    public Double open;         //开盘价
    public Double close;        //收盘价,当K线为最晚的一根时，是最新成交价
    public Double low;          //最低价
    public Double high;         //最高价
    public Double vol;          //成交额, 即 sum(每一笔成交价 * 该笔的成交量)
}
