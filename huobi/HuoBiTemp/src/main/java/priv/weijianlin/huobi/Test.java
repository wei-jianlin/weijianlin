package priv.weijianlin.huobi;

import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.OkHttpClient;
import priv.weijianlin.huobi.enums.PeriodEnum;
import priv.weijianlin.huobi.model.OrderDetails;
import priv.weijianlin.huobi.model.SimpleSymbolByKlineModel;
import priv.weijianlin.huobi.model.Symbol;
import priv.weijianlin.huobi.request.ApiClient;
import priv.weijianlin.huobi.request.CreateOrderRequest;
import priv.weijianlin.huobi.response.ApiException;
import priv.weijianlin.huobi.response.Kline;
import priv.weijianlin.huobi.strategy.KlineStrategy;
import priv.weijianlin.huobi.util.Arithmetic;
import priv.weijianlin.huobi.util.Constant;
import priv.weijianlin.huobi.util.ListUtil;
import priv.weijianlin.huobi.util.OkHttpClientUtil;

@SuppressWarnings("deprecation")
public class Test {
    
    static final OkHttpClient httpClient = OkHttpClientUtil.createOkHttpClient();
    static ApiClient client = ApiClient.getApiClient(httpClient);
    static List<Symbol> allSymbol = client.getSymbols(symbol -> 
	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("btcusdt") ||
	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("xrpusdt") ||
	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("ethusdt") ||
	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("ltcusdt") ||
	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("eosusdt") ||
	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("etcusdt"));
    static List<SimpleSymbolByKlineModel> buySymbol = KlineStrategy.simpleSymbolByKline(allSymbol);
    private static final Logger logger = LoggerFactory.getLogger(Test.class);
            
    public static void main(String[] args) {
        getAllSymbol();
        while(true){
            try{
                if(allSymbol != null && buySymbol != null){
                    buySymbol();
                }
            }catch(Exception e){
                logger.info("exception:" + e.getMessage());
            }

        }
    }

    //每隔两个星期更新 symbol
    private static void getAllSymbol(){
        Runnable runnable = new Runnable() {            
            public void run() { 
                logger.info("开始执行getAllSymbol()" + new Date().toLocaleString());
                List<Symbol> allSymbolBackups = null;
                List<SimpleSymbolByKlineModel> buySymbolBackups = null;
                try{
                    allSymbolBackups = ListUtil.deepCopy(allSymbol);
                    buySymbolBackups = ListUtil.deepCopy(buySymbol);
                    allSymbol = null;
                    buySymbol = null;
                    allSymbol = client.getSymbols(symbol -> 
                	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("btcusdt") ||
                	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("xrpusdt") ||
                	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("ethusdt") ||
                	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("ltcusdt") ||
                	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("eosusdt") ||
                	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("etcusdt"));
                    buySymbol = KlineStrategy.simpleSymbolByKline(allSymbol);
                }catch(SocketTimeoutException e){
                    if(allSymbol == null){
                        allSymbol = allSymbolBackups;
                    }
                    if(buySymbol == null){
                        buySymbol = buySymbolBackups;
                    }
                    logger.info("exception:" + e.getMessage());
                }catch(Exception e){
                    logger.info("exception:" + e.getMessage());
                }finally{
                    logger.info("执行完getAllSymbol()" + new Date().toLocaleString());
                }               
            }  
        };
        ScheduledExecutorService service = Executors  
                .newSingleThreadScheduledExecutor();  
        service.scheduleAtFixedRate(runnable,15,15,TimeUnit.DAYS);
    }
    
    //买入
    private static void buySymbol(){
        for(SimpleSymbolByKlineModel model : buySymbol){
            boolean isRisk = false;
            List<Kline> safeKlines = client.getHistoryKline(model.getSymbol(),PeriodEnum.DAY_1.getValue(),2);
            for(Kline kline : safeKlines) {
                double riseAndFall = Arithmetic.riseAndFall(kline.close, kline.high);              
                if(riseAndFall > 0.60) {
                    isRisk = true;
                    break;
                }
            }
            //昨日与今日涨幅超60%,今日忽略
            if(isRisk) continue;
            int totalRoot = model.getRoot() * 2;
            List<Kline> klines = client.getHistoryKline(model.getSymbol(), model.getPeriod(),totalRoot);
            totalRoot = klines.size();
            Kline currentKline = klines.get(0);    
            for(int i = 1; i < totalRoot; i++){
                Kline perKline = klines.get(i);
                double riseAndFall = Arithmetic.riseAndFall(currentKline.close, perKline.open);
                if(riseAndFall <= model.getBuyRise()){
                    try{
                        String buyPrice = new BigDecimal(currentKline.close).setScale(8,BigDecimal.ROUND_DOWN)
                                .toString();
                        String salePrice = new BigDecimal(currentKline.close * (1 + model.getSaleRise()))
                                .setScale(8,BigDecimal.ROUND_DOWN).toString();
                        Long buyOrderId = CreateOrderRequest.orderPlace(client, buyPrice, 
                                model.getSymbol(), Constant.BUY_LIMIT);
                        if(buyOrderId != null){
                            //挂单二十分钟
                            Thread.sleep(1200000);
                            OrderDetails orderDetails = client.orderDetail(buyOrderId.toString());
                            //部分成交或者完全成交
                            switch(orderDetails.getState()){
                            case "partial-filled":
                                CreateOrderRequest.submitcancel(client, buyOrderId.toString());
                                CreateOrderRequest.orderPlace(client, salePrice, model.getSymbol(), Constant.SELL_LIMIT);
                                logger.info("-----已成交买入价为" + buyPrice + "-----" + "已成交卖出价:" + salePrice);
                                break;
                            case "filled":
                                CreateOrderRequest.orderPlace(client, salePrice, model.getSymbol(), Constant.SELL_LIMIT);
                                logger.info("-----已成交买入价为" + buyPrice + "-----" + "已成交卖出价:" + salePrice);
                                break;
                            default:
                                CreateOrderRequest.submitcancel(client, buyOrderId.toString());
                            }                  
                        }else{
                            logger.info("-----未成交买入价为" + buyPrice + "-----" + "未成交卖出价:" + salePrice);
                        }
                    }catch(ApiException e){
                        logger.error("API Error! err-code: " + e.getErrCode() + ", err-msg: " + e.getMessage()
                                + ",paramter:" + model.getSymbol());
                    }catch(InterruptedException e) {
                        logger.error("Thread Error! err-msg: " + e.getMessage());
                    }catch (Exception e) {
                        logger.error("Thread Error! err-msg: " + e.getMessage());
                    }     
                }
            }
        }
    }
}

