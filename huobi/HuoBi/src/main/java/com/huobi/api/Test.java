package com.huobi.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huobi.api.response.Historytrade;
import com.huobi.api.response.HistorytradeDetail;
import com.huobi.api.response.Kline;
import com.huobi.api.response.Matchresults;
import com.huobi.api.util.ApiClient;
import com.huobi.api.util.Arithmetic;
import com.huobi.api.util.Constant;
import com.huobi.api.util.DateUtil;
import com.huobi.api.util.SymbolEnum;

public class Test {
    
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    
    public static void main(String[] args) {      
        ApiClient client = new ApiClient();
        //while(true)allSymbolMin30(client);
        //get(client,SymbolEnum.ITC_BTC,"2018-01-05 00:23:41","0.00043000");
        //getHistoryTrade(client);
        //SymbolEnum[] symbolEnums = SymbolEnum.values();        
        //for(SymbolEnum symbolEnum : symbolEnums){
            //CreateOrder.getMatchresults(client,symbolEnum,"2018-01-05","");
        //}
    }
    
    public static void allSymbolMin30(ApiClient client){
        SymbolEnum[] symbolEnums = SymbolEnum.values();        
        for(SymbolEnum symbolEnum : symbolEnums){
            try{
                List<Kline> klines = client.getHistoryKline(symbolEnum, Constant.DAY_1, 2);
                for(Kline kline : klines) {
                    double riseAndFall = Arithmetic.riseAndFall(kline.open, kline.high);
                    //昨日与今日涨幅超60%,今日忽略
                    if(riseAndFall < 0.60) {
                        min30(client,symbolEnum);
                        break;
                    }
                }
            }catch(ApiException e){
                logger.error("API Error! err-code: " + e.getErrCode() + ", err-msg: " + e.getMessage()
                        + ",paramter:" + symbolEnum);
            }catch (Exception e) {
                logger.error("Thread Error! err-msg: " + e.getMessage());
            }    
        }
    }
    

    /**4条30分钟线开盘价,每一条的收盘价与开盘价之差4条之和比 Constant.BUY_RISE1_30大的,买入
     * @param client
     * @param symbolEnum
     */
    public static void min30(ApiClient client,SymbolEnum symbolEnum){
        Calendar calendar = Calendar.getInstance();
        int miute = calendar.get(Calendar.MINUTE) % 30;
        calendar.add(Calendar.MINUTE, -miute);
        List<Kline> list = new ArrayList<Kline>(0);
        try{
           list = client.getHistoryKline(symbolEnum, Constant.MIN_30, 4);
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
                   String buyPrice = new BigDecimal(firstKline.close).setScale(8,BigDecimal.ROUND_DOWN)
                           .toString();
                   String salePrice = new BigDecimal(firstKline.close * (1 + Constant.SALE_RISE1_30))
                           .setScale(8,BigDecimal.ROUND_DOWN).toString();
                   logger.info("-----买入价为" + buyPrice + "-----" + "卖出价:" + salePrice);
               }
           }
        }catch(ApiException e){
            logger.error("API Error! err-code: " + e.getErrCode() + ", err-msg: " + e.getMessage()
                    + ",paramter:" + symbolEnum);
        }catch (Exception e) {
            logger.error("Thread Error! err-msg: " + e.getMessage());
        }       
    } 
    
    /** 
     * <p>获取特定币,特定时间价格</p>
     * <p>功能详细描述</p>
     * @param client
     * @param symbolEnum
     * @param dateStr 2018-01-03 11:10:36
     * @param salePrice 卖价
     */
    public static void get(ApiClient client,SymbolEnum symbolEnum,String dateStr,String salePrice){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BigDecimal realSalePrice = new BigDecimal(salePrice).multiply(
                new BigDecimal(1 + Constant.SALE_RISE1_30));
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date now = new Date();
        Long million = now.getTime() - date.getTime();
        int min30 = (int)(million / DateUtil.nh * 2);
        List<Kline> list = client.getHistoryKline(symbolEnum, Constant.MIN_30, 48);
        for(int i = 0; i < min30; i++){
            Kline kline = list.get(i);
            if(new BigDecimal(kline.high).compareTo(realSalePrice) > 0){
                System.out.println("经过" + i + "个半小时,成功卖出,卖价:" + realSalePrice 
                        + ",最高价:" + kline.high);
            }
        }
    }
    
    
    /** 
     * <p>批量获取最近的交易记录</p>
     * <p>不是自己的交易记录,是币的实时交易</p>
     * @param client
     */
    public static void getHistoryTrade(ApiClient client){
        SymbolEnum[] symbolEnums = SymbolEnum.values();
        for(SymbolEnum symbolEnum : symbolEnums){      
            List<Historytrade> historytrades = client.getHistoryTrade(symbolEnum, 30);
            for(Historytrade historytrade : historytrades){
                List<HistorytradeDetail> historytradeDetails = historytrade.data;
                for(HistorytradeDetail historytradeDetail : historytradeDetails){
                    System.out.println(symbolEnum.name() + ",成交量:" + historytradeDetail.amount
                            + ",价格:" + historytradeDetail.price + ",成交方向:" 
                            + historytradeDetail.direction + ",时间:" 
                            + new Date(historytradeDetail.ts).toLocaleString());
                }
            }
        }
    }
    /** 
     * <p>查询当前成交、历史成交</p>
     * <p>功能详细描述</p>
     * @param client
     * @param startDate  yyyy-mm-dd
     * @param endDate    yyyy-mm-dd
     */
    public static List<Matchresults> getMatchresults(ApiClient client,SymbolEnum symbolEnum,
            String startDate){
        List<Matchresults> list = client.getMatchresults(symbolEnum, startDate, "","","");
        return list;
    }
    
}