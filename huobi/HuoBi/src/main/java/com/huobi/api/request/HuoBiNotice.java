package com.huobi.api.request;

import java.io.IOException;

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

    private static OkHttpClient client = new OkHttpClient();
    
    private static String noticeListUrl = "https://www.huobi.com/p/api/contents/pro/list_notice?"
                    + "r=0kq6mp0r2k7a&limit=10&language=zh-cn";
    
    public static void main(String[] args) {
        try {
            System.out.println(run(noticeListUrl));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String run(String url) throws IOException{
        Request request = new Request.Builder().url(url).build();
        
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
