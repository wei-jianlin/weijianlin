package com.huobi.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huobi.api.response.Kline;
import com.huobi.api.util.Arithmetic;
import com.huobi.api.util.Constant;
import com.huobi.api.util.GenerateSymbolEnum;
import com.huobi.api.util.ApiClient;
import com.huobi.api.util.SymbolEnum;

/**
 * <p>选币</p>
 * @author cz
 * @version V1.0, 2018年1月24日
 * @see 包名.类名#类名
 * @since 2018年1月24日
 * deprecated 如果已经不再推荐使用，请在标识符前加上@符号
 */
public class ChooseCurrency {

    public static void main(String[] args) {
        //GenerateSymbolEnum.main(new String[]{});
        ApiClient client = new ApiClient();
        chooseCurrency(client);
    }

    /** 
     * <p>日线选币</p>
     * <p>1.当前涨幅前三的</p>
     * @param client
     */
    public static void chooseCurrency(ApiClient client){
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
/*            if(list.size() <= 5){
                System.out.println("新股:" + symbolEnum);              
            }*/
            
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
/*            //size天涨幅
            double rise = Arithmetic.riseAndFall(lastKline.open,todayKline.close);            
            if(rise > 1){
                System.out.println(symbolEnum + "涨幅:" + Arithmetic.riseAndFallToString(rise));
            }*/
        }
        Arithmetic.ranking(map, riseList, 3,"desc");       
    }
}
