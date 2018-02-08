package priv.weijianlin.huobi.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

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

public class KlineStrategy {

    static final OkHttpClient httpClient = OkHttpClientUtil.createOkHttpClient();
    static ApiClient client = ApiClient.getApiClient(httpClient);
    //static List<Symbol> allSymbol = client.getSymbols();
    private static final Logger logger = LoggerFactory.getLogger(KlineStrategy.class);
    
    public static void main(String[] args) {
        Symbol symbol = new Symbol();
        symbol.setSymbol("btcusdt");
        simpleSymbolByKline(symbol);
    }
    /** 
     * <p>按k线选币</p>
     * <p>单一币按不同跟K线,不同跌幅,不同涨幅交易,按权重,收益排序</p>
     * <p>权重越大,风险越小,选择权重前2/3中收益最高的.</p>
     * <p>选取30天数据参考,按不同period折算,不足30天的,跳过</p>
     */
    private static void simpleSymbolByKline(Symbol symbol){
        PeriodEnum[] periods = PeriodEnum.values();
        SortedSet<Integer> set = new TreeSet<Integer>();
        List<SimpleSymbolByKlineModel> list = new ArrayList<SimpleSymbolByKlineModel>();
        for(PeriodEnum period : periods){
            int totalRoot = PeriodEnum.getRoot(30, period);
            List<Kline> klines = client.getHistoryKline(symbol, period.getValue(),totalRoot);
            if(period.getValue().equals("1day") && klines.size() < 30) return;
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
                            model.setSymbol(symbol.getSymbol());
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
            logger.info(model.getSymbol() + model.getRoot() + "根" + model.getPeriod() 
                    + "线,买入:"+ Arithmetic.setScale(model.getBuyRise().toString(), "3")
                    + ",卖出:" + Arithmetic.setScale(model.getSaleRise().toString(),"3") 
                    + ",收益:" + Arithmetic.setScale(model.getTotalProfit().toString(),"3") 
                    + ",权重:" + model.getCount());
        }
        //选择权重1,2,3的第一条数据
        int i = 1;
        List<SimpleSymbolByKlineModel> filterList = new ArrayList<SimpleSymbolByKlineModel>(3);
        for(SimpleSymbolByKlineModel model : list){
            if(model.getCount() == i && i < 4){
                i++;
                filterList.add(model);
            }
        }
        logger.info("---------------我是分割线!----------------"); 
        for(SimpleSymbolByKlineModel model : filterList){
            logger.info(model.getSymbol() + model.getRoot() + "根" + model.getPeriod() 
                    + "线,买入:"+ Arithmetic.setScale(model.getBuyRise().toString(), "3")
                    + ",卖出:" + Arithmetic.setScale(model.getSaleRise().toString(),"3") 
                    + ",收益:" + Arithmetic.setScale(model.getTotalProfit().toString(),"3") 
                    + ",权重:" + model.getCount());
        }

    }
}