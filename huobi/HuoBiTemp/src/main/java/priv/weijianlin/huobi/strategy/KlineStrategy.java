package priv.weijianlin.huobi.strategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.OkHttpClient;
import priv.weijianlin.huobi.enums.PeriodEnum;
import priv.weijianlin.huobi.model.SimpleSymbolByKlineModel;
import priv.weijianlin.huobi.model.Symbol;
import priv.weijianlin.huobi.request.ApiClient;
import priv.weijianlin.huobi.response.Kline;
import priv.weijianlin.huobi.util.Arithmetic;
import priv.weijianlin.huobi.util.Constant;
import priv.weijianlin.huobi.util.OkHttpClientUtil;

@SuppressWarnings("unused")
public class KlineStrategy {

    static final OkHttpClient httpClient = OkHttpClientUtil.createOkHttpClient();
    static ApiClient client = ApiClient.getApiClient(httpClient);    
    private static final Logger logger = LoggerFactory.getLogger(KlineStrategy.class);
    
    public static void main(String[] args) {
    	List<Symbol> allSymbol = client.getSymbols(symbol -> 
    	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("btcusdt") ||
    	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("xrpusdt") ||
    	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("ethusdt") ||
    	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("ltcusdt") ||
    	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("eosusdt") ||
    	(symbol.getBaseCurrency() + symbol.getQuoteCurrency()).equals("etcusdt"));
        List<SimpleSymbolByKlineModel> buySymbol = simpleSymbolByKline(allSymbol);
    }
    /** 
     * <p>买入,选usdt主区,按k线选币</p>
     * <p>单一币按不同跟K线,不同跌幅,不同涨幅交易,按权重,收益排序</p>
     * <p>选取权重为2的,取数buyRise 和 saleRise 都降低0.01,k线,root 从 0 到 2 * root</p>
     * <p>选取30天数据参考,按不同period折算,不足30天的,跳过</p>
     */
    public static List<SimpleSymbolByKlineModel> simpleSymbolByKline(List<Symbol> allSymbol){
        List<SimpleSymbolByKlineModel> buySymbol = new ArrayList<SimpleSymbolByKlineModel>();
        for(Symbol symbol : allSymbol){
            PeriodEnum[] periods = PeriodEnum.values();
            SortedSet<Integer> set = new TreeSet<Integer>();
            List<SimpleSymbolByKlineModel> list = new ArrayList<SimpleSymbolByKlineModel>();
            for(PeriodEnum period : periods){
                int totalRoot = PeriodEnum.getRoot(30, period);
                List<Kline> klines = client.getHistoryKline(symbol, period.getValue(),totalRoot);
                System.out.println("simpleSymbolByKline运行了!");
                for(int root = 1; root <= totalRoot; root++){           //几根K线
                    //费率做为起步
                    for(Double buyRise = -Constant.RATE;buyRise > -0.7;buyRise-= Constant.RATE){
                        for(Double saleRise = Constant.RATE;saleRise < 2;saleRise+= Constant.RATE){
                            int count = 0;                      //交易次数,数值越大,权重越高.
                            Double buyPrice = null;             //买入价
                            Double salePrice = null;            //卖出价
                            Double totalProfit = 0.0;           //总收益        
                            //TODO 实际交易时从0开始,下面的取值可能也要改
                            Kline preKline = klines.get(klines.size() - 1);
                            for(int i = klines.size() - (root + 1); i >= 0; i-= root){
                                Kline currentKline = klines.get(i);
                                if(buyPrice != null){
                                    if(Double.compare(currentKline.high, salePrice) >= 0){
                                        count++;
                                        totalProfit += saleRise;
                                        buyPrice = null;
                                    }else if(i - root < 0 || i == 0){
                                        //一直没卖出去的,收益要减
                                        double rise = Arithmetic.riseAndFall(currentKline.close,buyPrice);
                                        totalProfit += rise;
                                    }
                                }else{
                                    double rise = Arithmetic.riseAndFall(currentKline.close,preKline.close);
                                    if(rise <= buyRise){
                                        buyPrice = preKline.close * (1 + buyRise);
                                        salePrice = buyPrice * (1 + saleRise);
                                    }
                                }
                                preKline = currentKline;
                            }
                            if(Double.compare(totalProfit, 0.0) > 0 && count > 0){
                                SimpleSymbolByKlineModel model = new SimpleSymbolByKlineModel();
                                model.setSymbol(symbol);
                                model.setRoot(root);
                                model.setCount(count);
                                model.setPeriod(period.getValue());
                                model.setBuyRise(buyRise);
                                model.setSaleRise(saleRise);
                                model.setTotalProfit(totalProfit);
                                list.add(model);
                                set.add(count);
                            }
                        }
                    } 
                }     
            }
            Collections.sort(list);                                                  //排序
            for(SimpleSymbolByKlineModel model : list){
                if(model.getCount() == 2 ){
                    Double buyRise = model.getBuyRise();
                    //buyRise是负的
                    model.setBuyRise(new BigDecimal(buyRise).add(new BigDecimal(0.001)).doubleValue());
                    Double saleRise = model.getSaleRise();
                    saleRise =  new BigDecimal(saleRise).add(new BigDecimal(-0.001)).doubleValue();
                    model.setSaleRise(saleRise);
                    if(saleRise.compareTo(0.005) == 1) {
                    	  buySymbol.add(model);
                    }                                    
                    break;
                }
            }
        }
        for(SimpleSymbolByKlineModel model : buySymbol){
            logger.info(model.getSymbol().getBaseCurrency() + model.getSymbol().getQuoteCurrency() 
                    + model.getRoot() + "根" + model.getPeriod() 
                    + "线,买入:"+ Arithmetic.setScale(model.getBuyRise().toString(), "3")
                    + ",卖出:" + Arithmetic.setScale(model.getSaleRise().toString(),"3") 
                    + ",收益:" + Arithmetic.setScale(model.getTotalProfit().toString(),"3") 
                    + ",权重:" + model.getCount());
        }
        return buySymbol;
    }
}
