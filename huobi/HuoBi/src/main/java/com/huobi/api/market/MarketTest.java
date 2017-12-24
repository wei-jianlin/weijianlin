package com.huobi.api.market;

import java.util.Calendar;
import java.util.List;

import com.huobi.api.resultMap.Kline;
import com.huobi.api.util.ApiClient;
import com.huobi.api.util.Arithmetic;
import com.huobi.api.util.Constant;
import com.huobi.api.util.SymbolEnum;

@SuppressWarnings("deprecation")
public class MarketTest {
	
    public static void main(String[] args) {
        ApiClient client = new ApiClient(Market.MARKET_HOST,Market.MARKET_URL);
        btcMin30(client);       
    }
    
   
	public static void btcMin30(ApiClient client){
    	Calendar calendar = Calendar.getInstance();
    	int miute = calendar.get(Calendar.MINUTE) % 30;
    	calendar.add(Calendar.MINUTE, -miute);
        List<Kline> list = client.getHistoryKline(SymbolEnum.BTC_USDT, Constant.MIN_30, 4);
        Kline firstKline = list.get(0);
        double riseAndFallTotal = 0;
        for(int i = 0; i < list.size(); i++) {
        	Kline kline = list.get(i);
        	double riseAndFall = Arithmetic.riseAndFall(kline.open, kline.close);
        	riseAndFallTotal += riseAndFall; 
        	System.out.println("BTC_USDT 30分钟线涨跌幅:" + 
        	Arithmetic.riseAndFallToString(Arithmetic.riseAndFall(kline.open, kline.close))
        	+ "  " + calendar.getTime().toLocaleString());
        	calendar.add(Calendar.MINUTE, -30);
        	if(riseAndFallTotal < Constant.BUY_RISE1_30) {
        		System.out.println("----------------买入1,买入价为" + firstKline.close
        				+ "-------------------");
        	}

        }
    }

}
