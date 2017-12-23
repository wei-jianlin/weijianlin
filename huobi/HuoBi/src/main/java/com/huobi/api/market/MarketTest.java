package com.huobi.api.market;

import java.io.IOException;

import com.huobi.api.util.ApiClient;
import com.huobi.api.util.Constant;
import com.huobi.api.util.JsonUtil;
import com.huobi.api.util.SymbolEnum;

public class MarketTest {

    public static void main(String[] args) {
        ApiClient client = new ApiClient(Market.MARKET_HOST,Market.MARKET_URL);
        print(client.getHistoryKline(SymbolEnum.BTC_USDT, Constant.DAY_1, 5));
    }
    

    static void print(Object obj) {
        try{
            System.out.println(JsonUtil.writeValue(obj));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
