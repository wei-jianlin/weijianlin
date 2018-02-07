package priv.weijianlin.huobi.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

public class OkHttpClientUtil {

    static final int CONN_TIMEOUT = 10;
    static final int READ_TIMEOUT = 10;
    static final int WRITE_TIMEOUT = 10;
    
    private OkHttpClientUtil(){}
    
    // create OkHttpClient:
    public static OkHttpClient createOkHttpClient() {
      return new Builder().connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
          .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
          .build();
    }
}
