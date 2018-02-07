package priv.weijianlin.huobi;

import okhttp3.OkHttpClient;
import priv.weijianlin.huobi.request.ApiClient;
import priv.weijianlin.huobi.util.OkHttpClientUtil;

public class Main {
    
    static final OkHttpClient httpClient = OkHttpClientUtil.createOkHttpClient();
    static ApiClient client = ApiClient.getApiClient(httpClient);
    
    public static void main(String[] args) {

    }

}
