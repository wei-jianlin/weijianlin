package com.huobi.api.request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.huobi.api.response.Kline;
import com.huobi.api.util.ApiClient;
import com.huobi.api.util.Arithmetic;
import com.huobi.api.util.Constant;
import com.huobi.api.util.SymbolEnum;

public class Risk {

	/**风险控制
	 * @param args
	 */
	public static void main(String[] args) {
		ApiClient client = new ApiClient();
		List<SymbolEnum> riskSymbolEnums = new ArrayList<SymbolEnum>(0);
		theDailIncreaseIsOverSixty(client,riskSymbolEnums);
		for(SymbolEnum riskSymbolEnum : riskSymbolEnums){
		    System.out.println(riskSymbolEnum);
		}
	}

	/**
	 * 一小时查询一次昨日与今日涨幅超60%,今日忽略
	 */
	public static void theDailIncreaseIsOverSixty(ApiClient client,List<SymbolEnum> riskSymbolEnums) {
        SymbolEnum[] symbolEnums = SymbolEnum.values();
        //一小时查询一次昨日与今日涨幅超60%,今日忽略       
        Runnable runnable = new Runnable() {  
            public void run() {  
                for(SymbolEnum symbolEnum : symbolEnums){
                    if(symbolEnum.equals(SymbolEnum.XRP_BTC)) {
                        List<Kline> klines = client.getHistoryKline(symbolEnum, Constant.DAY_1, 2);
                        for(Kline kline : klines) {
                            double riseAndFall = Arithmetic.riseAndFall(kline.open, kline.high);
                            if(riseAndFall > 0.01) {
                                riskSymbolEnums.add(symbolEnum);
                                break;
                            }
                        }
                    }

                }
            }  
        };
        ScheduledExecutorService service = Executors  
                .newSingleThreadScheduledExecutor();  
        service.scheduleAtFixedRate(runnable,1,5,TimeUnit.SECONDS);
	}

}
