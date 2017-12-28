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
import com.huobi.api.request.CreateOrderRequest;
import com.huobi.api.response.Account;
import com.huobi.api.response.ApiResponse;
import com.huobi.api.response.Kline;
import com.huobi.api.response.OrderDetails;
import com.huobi.api.response.Symbol;

public class ApiClient {
    
    static final int CONN_TIMEOUT = 5;
    static final int READ_TIMEOUT = 5;
    static final int WRITE_TIMEOUT = 5;
    static final MediaType JSON = MediaType.parse("application/json");
    
    static final OkHttpClient client = createOkHttpClient();
    
    static final String API_HOST = "api.huobi.pro";

    static final String API_URL = "https://" + API_HOST;
    
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
                get("/market/history/kline", params, new TypeReference<ApiResponse<List<Kline>>>() {});
            return resp.checkAndReturn();
    }

    /**
     * 查询所有账户信息
     * 
     * @return List of accounts.
     */
    public List<Account> getAccounts() {
      ApiResponse<List<Account>> resp =
          get("/v1/account/accounts", null, new TypeReference<ApiResponse<List<Account>>>() {});
      return resp.checkAndReturn();
    }
    
    /** 
     * <p>查询指定账户的余额</p>
     * <p>功能详细描述</p>
     * @return
     */
    public Account getBalance(){
        ApiResponse<Account> resp =
                get("/v1/account/accounts/" + AccountInfo.SPOT_ACCOUNT_ID + "/balance", null, 
                        new TypeReference<ApiResponse<Account>>() {});
            return resp.checkAndReturn();
    } 
    
    /**
     * 创建订单
     * 
     * @param request CreateOrderRequest object.
     * @return Order id.
     */
    public Long createOrder(CreateOrderRequest request) {
      ApiResponse<Long> resp =
          post("/v1/order/orders/place", request, new TypeReference<ApiResponse<Long>>() {});
      return resp.checkAndReturn();
    }
    
    /** 
     * <p>撤销订单</p>
     * <p>订单是否撤销成功请调用订单查询接口查询该订单状态</p>
     * @param orderId
     * @return Order id.
     */
    public String submitcancel(String orderId){
        ApiResponse<String> resp =
                post("/v1/order/orders/" + orderId + "/submitcancel", null, 
                        new TypeReference<ApiResponse<String>>() {});
            return resp.checkAndReturn();
    }
    /** 
     * <p>查询订单详情</p>
     * <p>功能详细描述</p>
     * @param orderId
     */
    public OrderDetails orderDetail(String orderId){
        ApiResponse<OrderDetails> resp =
                get("/v1/order/orders/" + orderId, null, 
                        new TypeReference<ApiResponse<OrderDetails>>() {});
            return resp.checkAndReturn();
    }
    /**
     * 查询交易对
     * 
     * @return List of symbols.
     */
    public List<Symbol> getSymbols() {
      ApiResponse<List<Symbol>> resp =
          get("/v1/common/symbols", null, new TypeReference<ApiResponse<List<Symbol>>>() {});
      return resp.checkAndReturn();
    }
    
    // send a GET request.
    <T> T get(String uri, Map<String, String> params, TypeReference<T> ref) {
      if (params == null) {
        params = new HashMap<>();
      }
      return call("GET", uri, null, params, ref);
    }
    
    // send a POST request.
    <T> T post(String uri, Object object, TypeReference<T> ref) {
      return call("POST", uri, object, new HashMap<String, String>(), ref);
    }

    
    // call api by endpoint.
    <T> T call(String method, String uri, Object object, Map<String, String> params,
        TypeReference<T> ref) {
      ApiSignature sign = new ApiSignature();
      sign.createSignature(AccountInfo.API_KEY, AccountInfo.API_SECRET, method, API_HOST, uri, params);
      try {
        Request.Builder builder = null;
        if ("POST".equals(method)) {
          RequestBody body = RequestBody.create(JSON, JsonUtil.writeValue(object));
          builder = new Request.Builder().url(API_URL + uri + "?" + toQueryString(params)).post(body);
        } else {
          builder = new Request.Builder().url(API_URL + uri + "?" + toQueryString(params)).get();
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