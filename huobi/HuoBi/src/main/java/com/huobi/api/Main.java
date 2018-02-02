package com.huobi.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huobi.api.request.CreateOrder;
import com.huobi.api.response.Kline;
import com.huobi.api.response.OrderDetails;
import com.huobi.api.util.ApiClient;
import com.huobi.api.util.Arithmetic;
import com.huobi.api.util.Constant;
import com.huobi.api.util.PeriodEnum;
import com.huobi.api.util.SymbolEnum;
import com.huobi.api.util.SysmbolStrategyByKlineEnum;

/**
 * <p>2月1日usdt:1665</p>
 * <p></p>
 * @version V1.0, 2018年2月1日
 */
public class Main {
    
    private static ApiClient client = new ApiClient();
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) {       
        while(true)simpleSymbolByKline();
    }
    
    
    public static void simpleSymbolByKline(){
        SysmbolStrategyByKlineEnum[] enums = SysmbolStrategyByKlineEnum.values();
        for(SysmbolStrategyByKlineEnum symbol : enums){
            try{
                List<Kline> klines = client.getHistoryKline(
                        SymbolEnum.valueOf(symbol.name()), PeriodEnum.DAY_1.getValue(), 2);
                for(Kline kline : klines) {
                    double riseAndFall = Arithmetic.riseAndFall(kline.open, kline.high);
                    //昨日与今日涨幅超60%,今日忽略
                    if(riseAndFall < 0.60) {
                        simpleSymbolByKline(symbol);
                        break;
                    }
                }
            }catch(ApiException e){
                logger.error("API Error! err-code: " + e.getErrCode() + ", err-msg: " + e.getMessage()
                        + ",paramter:" + symbol);
            }catch (Exception e) {
                logger.error("Thread Error! err-msg: " + e.getMessage());
            }              
        }
    }
    public static void simpleSymbolByKline(SysmbolStrategyByKlineEnum symbol){
        Calendar calendar = PeriodEnum.getKlineTime(symbol.getPeriod());
        List<Kline> list = new ArrayList<Kline>(symbol.getRoot());
        try{
           SymbolEnum symbolEnum = SymbolEnum.valueOf(symbol.name());
           list = client.getHistoryKline(symbolEnum,symbol.getPeriod(), symbol.getRoot());
           Kline preKline = list.get(list.size() - 1);
           Kline currentKline = list.get(0);
           double rise = Arithmetic.riseAndFall(preKline.open,currentKline.close);
           logger.info(symbolEnum + "," + symbol.getRoot() + "跟" + symbol.getPeriod() + "线涨跌幅:" 
                   + Arithmetic.riseAndFallToString(rise)
                   + "  " + calendar.getTime().toLocaleString());
           if(rise <= symbol.getBuyRise()){
               String buyPrice = new BigDecimal(currentKline.close).setScale(8,BigDecimal.ROUND_DOWN)
                       .toString();
               String salePrice = new BigDecimal(currentKline.close * (1 + symbol.getSaleRise()))
                       .setScale(8,BigDecimal.ROUND_DOWN).toString();
               Long buyOrderId = CreateOrder.orderPlace(client, buyPrice, 
                       symbolEnum, Constant.BUY_LIMIT);
               if(buyOrderId != null){
                   //挂单四分钟
                   Thread.sleep(240000);
                   OrderDetails orderDetails = client.orderDetail(buyOrderId.toString());
                   //部分成交或者完全成交
                   switch(orderDetails.state){
                   case "partial-filled":
                       CreateOrder.submitcancel(client, buyOrderId.toString());
                       CreateOrder.orderPlace(client, salePrice, symbolEnum, Constant.SELL_LIMIT);
                       logger.info("-----已成交买入价为" + buyPrice + "-----" + "已成交卖出价:" + salePrice);
                       break;
                   case "filled":
                       CreateOrder.orderPlace(client, salePrice, symbolEnum, Constant.SELL_LIMIT);
                       logger.info("-----已成交买入价为" + buyPrice + "-----" + "已成交卖出价:" + salePrice);
                       break;
                   default:
                       CreateOrder.submitcancel(client, buyOrderId.toString());
                   }                  
               }else{
                   logger.info("-----未成交买入价为" + buyPrice + "-----" + "未成交卖出价:" + salePrice);
               }             
           }
        }catch(ApiException e){
            logger.error("API Error! err-code: " + e.getErrCode() + ", err-msg: " + e.getMessage()
                    + ",paramter:" + symbol);
        }catch(InterruptedException e) {
            logger.error("Thread Error! err-msg: " + e.getMessage());
        }catch (Exception e) {
            logger.error("Thread Error! err-msg: " + e.getMessage());
        }       
    }
    public static void allSymbolMin30(){
        SymbolEnum[] symbolEnums = SymbolEnum.values();        
        for(SymbolEnum symbolEnum : symbolEnums){
            try{
                List<Kline> klines = client.getHistoryKline(symbolEnum, Constant.DAY_1, 2);
                for(Kline kline : klines) {
                    double riseAndFall = Arithmetic.riseAndFall(kline.open, kline.high);
                    //昨日与今日涨幅超60%,今日忽略
                    if(riseAndFall < 0.60) {
                        min30(symbolEnum);
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
    public static void min30(SymbolEnum symbolEnum){
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
                   Long buyOrderId = CreateOrder.orderPlace(client, buyPrice, symbolEnum, Constant.BUY_LIMIT);                  
                   if(buyOrderId != null) {
                       //挂单三分钟
                       Thread.sleep(180000);
                       OrderDetails orderDetails = client.orderDetail(buyOrderId.toString());
                       //部分成交或者完全成交
                       switch(orderDetails.state){
                       case "partial-filled":
                           CreateOrder.orderPlace(client, salePrice, symbolEnum, Constant.SELL_LIMIT);
                           //挂单两分钟
                           Thread.sleep(120000);
                           CreateOrder.submitcancel(client, buyOrderId.toString());
                           CreateOrder.orderPlace(client, salePrice, symbolEnum, Constant.SELL_LIMIT);
                           break;
                       case "filled":
                           CreateOrder.orderPlace(client, salePrice, symbolEnum, Constant.SELL_LIMIT);
                           break;
                       default:
                           CreateOrder.submitcancel(client, buyOrderId.toString());
                       }
                       logger.info("-----买入价为" + buyPrice + "-----" + "卖出价:" + salePrice);
                   }else{
                       logger.info("-----策略买入价为" + buyPrice + "-----" + "策略卖出价:" + salePrice);
                   }
               }
           }
        }catch(ApiException e){
            logger.error("API Error! err-code: " + e.getErrCode() + ", err-msg: " + e.getMessage()
                    + ",paramter:" + symbolEnum);
        }catch(InterruptedException e) {
            logger.error("Thread Error! err-msg: " + e.getMessage());
        }catch (Exception e) {
            logger.error("Thread Error! err-msg: " + e.getMessage());
        }       
    } 
}