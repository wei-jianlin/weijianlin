package com.huobi.api.request;

import java.util.ArrayList;
import java.util.List;

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
		List<SymbolEnum> riskSymbolEnums = theDailIncreaseIsOverSixty(client);
		for(SymbolEnum riskSymbolEnum :riskSymbolEnums) {
			System.out.println(riskSymbolEnum.name());
		}
	}

	/**
	 * 一小时查询一次昨日与今日涨幅超60%,今日忽略
	 */
	public static List<SymbolEnum> theDailIncreaseIsOverSixty(ApiClient client) {
		List<SymbolEnum> riskSymbolEnums = new ArrayList<SymbolEnum>(0);
        SymbolEnum[] symbolEnums = SymbolEnum.values();
        for(SymbolEnum symbolEnum : symbolEnums){
        	if(symbolEnum.equals(SymbolEnum.XRP_BTC)) {
            	List<Kline> klines = client.getHistoryKline(symbolEnum, Constant.DAY_1, 2);
            	for(Kline kline : klines) {
            		double riseAndFall = Arithmetic.riseAndFall(kline.open, kline.high);
            		if(riseAndFall > 0.60) {
            			riskSymbolEnums.add(symbolEnum);
            			break;
            		}
            	}
        	}

        }
		return riskSymbolEnums;
	}

}
