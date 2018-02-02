package com.huobi.api.request;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.huobi.api.response.Notice;
import com.huobi.api.response.NoticeList;
import com.huobi.api.response.WebpageResponse;
import com.huobi.api.util.ApiClient;
import com.huobi.api.util.Constant;
import com.huobi.api.util.JsonUtil;
import com.huobi.api.util.SymbolEnum;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>获得火币公告列表</p>
 * <p>功能详细描述</p>
 * @since 2018年1月25日
 * deprecated 如果已经不再推荐使用，请在标识符前加上@符号
 */
public class HuoBiNotice {

    private static Integer readId = null;                 
    private static ApiClient client = new ApiClient();
    private static OkHttpClient httpClient = new OkHttpClient();
    
    private static String noticeListUrl = "https://www.huobi.com/p/api/contents/pro/list_notice?"
                    + "r=0kq6mp0r2k7a&limit=10&language=zh-cn";
    static {
        try {
            WebpageResponse<NoticeList> obj = run();
            Notice notice = obj.getData().getItems().get(0);
            readId = notice.getId();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        while(true)
        try {
            WebpageResponse<NoticeList> obj = run();
            Notice notice = obj.getData().getItems().get(0);
            String title = notice.getTitle();
            if(notice.getId().compareTo(readId) != 0 && title.endsWith("/USDT交易")){
                readId = notice.getId();
                String symbol = title.substring(title.indexOf("上线") + 2, title.indexOf("/"));
                buyMarketAndSellMarket(SymbolEnum.valueOf(symbol + "_BTC"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 
     * <p>市价买btc_usdt,之后再市价买sysmbolm,30秒之后再市价卖sysmbolm,在换成usdt</p>
     * <p>功能详细描述</p>
     * @param sysmbol 已btc计价的货币
     * @throws InterruptedException 
     */
    private static void buyMarketAndSellMarket(SymbolEnum sysmbol) throws InterruptedException{
        CreateOrder.orderPlace(client,null,SymbolEnum.BTC_USDT,Constant.BUY_MARKET);
        CreateOrder.orderPlace(client,null,sysmbol,Constant.BUY_MARKET);
        Thread.sleep(30000);
        CreateOrder.orderPlace(client,null,sysmbol,Constant.SELL_MARKET);
        CreateOrder.orderPlace(client,null,SymbolEnum.BTC_USDT,Constant.SELL_MARKET);
    }
    
    private static WebpageResponse<NoticeList> run() throws IOException{
        Request request = new Request.Builder().url(noticeListUrl).build();        
        Response response = httpClient.newCall(request).execute();
        return JsonUtil.readValue(response.body().string(), 
                new TypeReference<WebpageResponse<NoticeList>>() {});
    }
}
