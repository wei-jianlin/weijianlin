package com.huobi.api;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huobi.api.model.SimpleSymbolByKlineModel;
import com.huobi.api.response.Kline;
import com.huobi.api.util.Arithmetic;
import com.huobi.api.util.Constant;
import com.huobi.api.util.ApiClient;
import com.huobi.api.util.PeriodEnum;
import com.huobi.api.util.SymbolEnum;

/**
 * <p>选币</p>
 * <p>各种策略选币</p>
 * @author cz
 * @version V1.0, 2018年1月24日
 * deprecated 如果已经不再推荐使用，请在标识符前加上@符号
 */
public class ChooseCurrency {

    private static ApiClient client = new ApiClient();
    private static final Logger logger = LoggerFactory.getLogger(ChooseCurrency.class);
            
    public static void main(String[] args) {
        //TODO 记得更新
        //GenerateSymbolEnum.main(new String[]{});       
        //chooseCurrency();
        generateSysmbolStrategyByKlineEnum();
    }

    /** 
     * <p>日线选币</p>
     * <p>1.当前涨幅前三的</p>
     * @param client
     */
    public static void chooseCurrency(){
        SymbolEnum[] symbolEnums = SymbolEnum.values();
        //当前涨幅
        Map<String,Double> map = new HashMap<String,Double>();
        //当前涨幅
        List<Double> riseList = new ArrayList<Double>();
        for(SymbolEnum symbolEnum : symbolEnums){
            int size = 15;
            List<Kline> list = null;
            try{
                list = client.getHistoryKline(symbolEnum, Constant.DAY_1, size);
            }catch(ApiException e){
                System.out.println("异常:" + symbolEnum);
                continue;
            }
            //找新股
            if(list.size() <= 5){
                System.out.println("新股:" + symbolEnum);              
            }
            
            Kline todayKline =  list.get(0);
            Kline lastKline = null;
            while(size >= 0){
                size = list.size();
                lastKline =  list.get(--size);
                if(lastKline != null) break;
            }
            //当前涨幅
            double todayRise = Arithmetic.riseAndFall(todayKline.open,todayKline.close);
            map.put(symbolEnum.name(), todayRise);
            riseList.add(todayRise);
            //size天涨幅
            double rise = Arithmetic.riseAndFall(lastKline.open,todayKline.close);            
            if(rise > 1){
                //System.out.println(symbolEnum + "涨幅:" + Arithmetic.riseAndFallToString(rise));
            }
        }
        Arithmetic.ranking(map, riseList, 3,"desc");       
    }
    private static void generateSysmbolStrategyByKlineEnum(){
        StringBuilder str = new StringBuilder("package com.huobi.api.util;\r\n\r\npublic enum ")
            .append("SysmbolStrategyByKlineEnum {\r\n\r\n\t");
        SymbolEnum[] enums = SymbolEnum.values();
        List<SymbolEnum> list = new ArrayList<SymbolEnum>();
        for(SymbolEnum symbol : enums){
            if(symbol.getValue().endsWith("usdt")){
                list.add(symbol);               
            }           
        }
        String contens = "";
        for(int i = 0; i < list.size(); i++){
            String content = simpleSymbolByKline(list.get(i));
            if(content != null){
                contens += content;
            }            
        }
        str.append(contens.substring(0,contens.length() - 4) + ";\r\n\t");
        str = classEnd(str);
        byte[] bytes = str.toString().getBytes();
        String projectPath = System.getProperty("user.dir");
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(projectPath
                    + "/src/main/java/com/huobi/api/util/SysmbolStrategyByKlineEnum.java");
            output.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(output != null){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /** 
     * <p>按k线选币</p>
     * <p>单一币按不同跟K线,不同跌幅,不同涨幅交易,按权重,收益排序</p>
     * <p>权重越大,风险越小,选择权重前2/3中收益最高的.</p>
     * <p>选取30天数据参考,按不同period折算,不足30天的,跳过</p>
     */
    private static String simpleSymbolByKline(SymbolEnum symbolEnum){
        PeriodEnum[] periods = PeriodEnum.values();
        SortedSet<Integer> set = new TreeSet<Integer>();
        List<SimpleSymbolByKlineModel> list = new ArrayList<SimpleSymbolByKlineModel>();
        for(PeriodEnum period : periods){            
            List<Kline> klines = client.getHistoryKline(symbolEnum, period.getValue(),
                    PeriodEnum.getRoot(30, period));
            if(period.getValue().equals("1day") && klines.size() < 30) return null;
            for(int root = 1; root <= 4; root++){           //几根K线
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
                                if(Double.compare(currentKline.close, salePrice) >= 0){
                                    count++;
                                    totalProfit += saleRise;
                                    buyPrice = null;
                                }
                            }else{
                                double rise = Arithmetic.riseAndFall(preKline.open,currentKline.close);
                                if(rise <= buyRise){
                                    //因为是测试已经发生的K线,但实际是正在发生的,达到buyRise就会买,所以买入价不是close.
                                    rise -= buyRise;
                                    buyPrice = currentKline.close * (-rise + 1);
                                    salePrice = buyPrice * (saleRise + 1);
                                }
                            }
                            preKline = currentKline;
                        }
                        if(Double.compare(totalProfit, 0.0) != 0){
                            SimpleSymbolByKlineModel model = new SimpleSymbolByKlineModel();
                            model.setSymbol(symbolEnum.name());
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
        logger.info("---------------我是分割线!----------------"); 
        Collections.sort(list);                                                  //权重排序
        Object[] objs = set.toArray();                                           //选择权重前2/3中收益最高的
        List<SimpleSymbolByKlineModel> filterList = list.stream().filter((SimpleSymbolByKlineModel o) ->{
            for(int i = objs.length * 2 / 3; i < objs.length; i++){
                if(o.getCount() == (int)objs[i]){
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());            
        Collections.sort(filterList,new Comparator<SimpleSymbolByKlineModel>(){
            @Override
            public int compare(SimpleSymbolByKlineModel o1,
                    SimpleSymbolByKlineModel o2) {
                int i = 0;
                if(o1.getTotalProfit() > o2.getTotalProfit()){
                    i = -1;
                }else if(o1.getTotalProfit() < o2.getTotalProfit()){
                    i = 1;
                }else{
                    if(o1.getBuyRise() < o2.getBuyRise()){
                        i = -1;
                    }else if(o1.getBuyRise() > o2.getBuyRise()){
                        i = 1;
                    }else{
                        if(o1.getSaleRise() < o2.getSaleRise()){
                            i = -1;
                        }else if(o1.getSaleRise() > o2.getSaleRise()){
                            i = 1;
                        }                        
                    }
                }
                return i;
            }});
        for(SimpleSymbolByKlineModel model : filterList){
            logger.info(model.getSymbol() + model.getRoot() + "根" + model.getPeriod() 
                    + "线,买入:"+ Arithmetic.setScale(model.getBuyRise().toString(), "3")
                    + ",卖出:" + Arithmetic.setScale(model.getSaleRise().toString(),"3") 
                    + ",收益:" + Arithmetic.setScale(model.getTotalProfit().toString(),"3") 
                    + ",权重:" + model.getCount());
        }
        SimpleSymbolByKlineModel firstModel = filterList.get(0);
        StringBuilder str = new StringBuilder();
        str.append(firstModel.getSymbol() + "(" + firstModel.getRoot() + ",\"" 
                + firstModel.getPeriod() + "\"," 
                + Arithmetic.setScale(firstModel.getBuyRise().toString(), "3") + "," 
                + Arithmetic.setScale(firstModel.getSaleRise().toString(),"3") + "),\r\n\t");
        return str.toString();
    }
    
    public static StringBuilder classEnd(StringBuilder str){
        str.append("\r\n\tprivate int root;             //几根");
        str.append("\r\n\tprivate String period;        //K线类别");
        str.append("\r\n\tprivate double buyRise;       //买入位置");
        str.append("\r\n\tprivate double saleRise;      //卖出位置");
        str.append("\r\n\r\n\tprivate SysmbolStrategyByKlineEnum(int root,String period,"
                + "double buyRise,double saleRise){");
        str.append("\r\n\t\tthis.root = root;");
        str.append("\r\n\t\tthis.period = period;");
        str.append("\r\n\t\tthis.buyRise = buyRise;");
        str.append("\r\n\t\tthis.saleRise = saleRise;\r\n\t}");
        str.append("\r\n\r\n\tpublic int getRoot(){\r\n\t\treturn this.root;\r\n\t}");
        str.append("\r\n\r\n\tpublic String getPeriod(){\r\n\t\treturn this.period;\r\n\t}");
        str.append("\r\n\r\n\tpublic double getBuyRise(){\r\n\t\treturn this.buyRise;\r\n\t}");
        str.append("\r\n\r\n\tpublic double getSaleRise(){\r\n\t\treturn this.saleRise;\r\n\t}");
        str.append("\r\n}");
        return str;
    }
}
