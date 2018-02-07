package priv.weijianlin.huobi;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import priv.weijianlin.huobi.model.Symbol;
import priv.weijianlin.huobi.request.ApiClient;
import priv.weijianlin.huobi.util.OkHttpClientUtil;

public class Test {
    
    static final OkHttpClient httpClient = OkHttpClientUtil.createOkHttpClient();
    static ApiClient client = ApiClient.getApiClient(httpClient);
    static List<Symbol> allSymbol = client.getSymbols();
    
    public static void main(String[] args) {
        while(true){
            if(allSymbol == null){
                System.out.println("null");
            }else{
                System.out.println(allSymbol.size());
            }
            getAllSymbol();
        }
    }

    //每隔两个星期更新 symbol
    public static void getAllSymbol(){
        Runnable runnable = new Runnable() {  
            public void run() { 
                allSymbol = null;
                allSymbol = client.getSymbols();
            }  
        };
        ScheduledExecutorService service = Executors  
                .newSingleThreadScheduledExecutor();  
        service.scheduleAtFixedRate(runnable,1,10,TimeUnit.SECONDS);
    }
}

