package com.huobi.api.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.OkHttpClient.Builder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.huobi.api.ApiException;
import com.huobi.api.util.ApiSignature;
import com.huobi.api.util.JsonUtil;
import com.huobi.api.response.ApiResponse;
import com.huobi.api.resultMap.Kline;

public class ApiClient {
    
    static final int CONN_TIMEOUT = 5;
    static final int READ_TIMEOUT = 5;
    static final int WRITE_TIMEOUT = 5;
    static final MediaType JSON = MediaType.parse("application/json");
    static final OkHttpClient client = createOkHttpClient();
    
    public String apiHost;
    public String apiUrl;
    
    public ApiClient(){}
    
    public ApiClient(String apiHost,String apiUrl){
        this.apiHost = apiHost;
        this.apiUrl = apiUrl;
    }
    
    /** 
     * <p>行情API</p>
     * <p>获取K线数据</p>
     * @param symbol 交易对
     * @param period K线类型
     * @param size
     * @return
     */
    public List<Kline> getHistoryKline(SymbolEnum symbol,String period,Integer size){
        Map<String, String> params = new HashMap<String,String>();
        params.put("symbol", symbol.getValue());
        params.put("period", period);
        params.put("size", size.toString());
        ApiResponse<List<Kline>> resp =
                get("/history/kline", params, new TypeReference<ApiResponse<List<Kline>>>() {});
            return resp.checkAndReturn();
    }

    // send a GET request.
    <T> T get(String uri, Map<String, String> params, TypeReference<T> ref) {
      if (params == null) {
        params = new HashMap<>();
      }
      return call("GET", uri, null, params, ref);
    }
    
    // call api by endpoint.
    <T> T call(String method, String uri, Object object, Map<String, String> params,
        TypeReference<T> ref) {
      ApiSignature sign = new ApiSignature();
      sign.createSignature(AccountInfo.API_KEY, AccountInfo.API_SECRET, method, this.apiHost, uri, params);
      try {
        Request.Builder builder = null;
        if ("POST".equals(method)) {
          RequestBody body = RequestBody.create(JSON, JsonUtil.writeValue(object));
          builder = new Request.Builder().url(apiUrl + uri + "?" + toQueryString(params)).post(body);
        } else {
          builder = new Request.Builder().url(apiUrl + uri + "?" + toQueryString(params)).get();
        }
        if (AccountInfo.API_KEY != null) {
          builder.addHeader("AuthData", authData());
        }
        Request request = builder.build();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        return JsonUtil.readValue(s, ref);
      } catch (IOException e) {
        throw new ApiException(e);
      }
    }
    
    String authData() {
        MessageDigest md = null;
        try {
          md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
          throw new RuntimeException(e);
        }
        md.update(AccountInfo.ASSET_PASSWORD.getBytes(StandardCharsets.UTF_8));
        md.update("hello, moto".getBytes(StandardCharsets.UTF_8));
        Map<String, String> map = new HashMap<>();
        map.put("assetPwd", DatatypeConverter.printHexBinary(md.digest()).toLowerCase());
        try {
          return ApiSignature.urlEncode(JsonUtil.writeValue(map));
        } catch (IOException e) {
          throw new RuntimeException("Get json failed: " + e.getMessage());
        }
      }

      // Encode as "a=1&b=%20&c=&d=AAA"
      String toQueryString(Map<String, String> params) {
        return String.join("&", params.entrySet().stream().map((entry) -> {
          return entry.getKey() + "=" + ApiSignature.urlEncode(entry.getValue());
        }).collect(Collectors.toList()));
      }

      // create OkHttpClient:
      static OkHttpClient createOkHttpClient() {
        return new Builder().connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .build();
      }
}
