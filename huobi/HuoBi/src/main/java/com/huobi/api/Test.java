package com.huobi.api;

import java.io.IOException;
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
import com.huobi.api.util.JsonUtil;
import com.huobi.api.util.SymbolEnum;

public class Test {
    
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    
    public static void main(String[] args) {
        try {
            ApiClient client = new ApiClient();
            while(true)allSymbolMin30(client);
            //accounts(client);
            //getBalance(client);
          } catch (ApiException e) {
              logger.error("API Error! err-code: " + e.getErrCode() + ", err-msg: " + e.getMessage());
              e.printStackTrace();
          }
    }
    public static void allSymbolMin30(ApiClient client){
        SymbolEnum[] symbolEnums = SymbolEnum.values();
        for(SymbolEnum symbolEnum : symbolEnums){
            min30(client,symbolEnum);
        }
    }
    public static void min30(ApiClient client,SymbolEnum symbolEnum){
        Calendar calendar = Calendar.getInstance();
        int miute = calendar.get(Calendar.MINUTE) % 30;
        calendar.add(Calendar.MINUTE, -miute);
        List<Kline> list = new ArrayList<Kline>(0);
        try{
           list = client.getHistoryKline(symbolEnum, Constant.MIN_30, 4);
        }catch(ApiException e){
            logger.error("API Error! err-code: " + e.getErrCode() + ", err-msg: " + e.getMessage()
                    + ",paramter:" + symbolEnum);
        }
        Kline firstKline = null;
        double riseAndFallTotal = 0;
        for(int i = 0; i < list.size(); i++) {
            firstKline = list.get(0);
            Kline kline = list.get(i);
            double riseAndFall = Arithmetic.riseAndFall(kline.open, kline.close);
            riseAndFallTotal += riseAndFall; 
            
            logger.info(symbolEnum + " 30分钟线涨跌幅:" + 
            Arithmetic.riseAndFallToString(Arithmetic.riseAndFall(kline.open, kline.close))
            + "  " + calendar.getTime().toLocaleString());
            calendar.add(Calendar.MINUTE, -30);
            if(riseAndFallTotal < Constant.BUY_RISE1_30) {
                logger.info("-----买入价为" + 
                            new BigDecimal(firstKline.close).setScale(8,BigDecimal.ROUND_DOWN) + 
                            "-----" + "理论卖出价:" + new BigDecimal(firstKline.close * 
                                    (1 + Constant.SALE_RISE1_30)).setScale(8,BigDecimal.ROUND_DOWN));
                break;
            }
        }
    }
    
    public static void getBalance(ApiClient client){
        print(client.getBalance());
    }
    
    public static void accounts(ApiClient client){
        print(client.getAccounts());
    }
    
    static void print(Object obj) {
        try {
          System.out.println(JsonUtil.writeValue(obj));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
}
