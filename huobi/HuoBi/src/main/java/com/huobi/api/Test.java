package com.huobi.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huobi.api.response.Kline;
import com.huobi.api.util.ApiClient;
import com.huobi.api.util.Arithmetic;
import com.huobi.api.util.Constant;
import com.huobi.api.util.SymbolEnum;

/**测试策略
 * @author weijianlin
 *
 */
public class Test {
    
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    
    public static void main(String[] args) {
        ApiClient client = new ApiClient();
        while(true)allSymbolMin30(client);
    }
    
    public static void allSymbolMin30(ApiClient client){
        SymbolEnum[] symbolEnums = SymbolEnum.values();
        for(SymbolEnum symbolEnum : symbolEnums){
            min30(client,symbolEnum);
        }
    }
    

    /**4条30分钟线开盘价,第一条的最低价与第4条的最高价之差比 0.087大的,买入,以0.037卖出
     * @param client
     * @param symbolEnum
     */
    public static void min30(ApiClient client,SymbolEnum symbolEnum){
        Calendar calendar = Calendar.getInstance();
        int miute = calendar.get(Calendar.MINUTE) % 30;
        calendar.add(Calendar.MINUTE, -miute);
        List<Kline> list = new ArrayList<Kline>(0);
        try {
        	list = client.getHistoryKline(symbolEnum, Constant.MIN_30, 4);
            Kline firstKline = list.get(0);
            Kline lastKline = list.get(3);
            double riseAndFallTotal = Arithmetic.riseAndFall(lastKline.high, firstKline.low);
            logger.info(symbolEnum + " 2小时涨跌幅:" + 
            Arithmetic.riseAndFallToString(Arithmetic.riseAndFall(lastKline.high, firstKline.low))
            + "  " + calendar.getTime().toLocaleString());
            if(riseAndFallTotal < -0.087){
                String salePrice = new BigDecimal(firstKline.close * (1 + 0.037))
                        .setScale(8,BigDecimal.ROUND_DOWN).toString();
                logger.info("-----买入价为" + Arithmetic.setScale(
                		firstKline.close.toString(), symbolEnum.getPricePrecision()) 
                			+ "-----" + "卖出价:" + salePrice);
            }
        }catch (ApiException e) {
            logger.error("API Error! err-code: " + e.getErrCode() + ", err-msg: " + e.getMessage()
            + ",paramter:" + symbolEnum);
		}       
    } 
}