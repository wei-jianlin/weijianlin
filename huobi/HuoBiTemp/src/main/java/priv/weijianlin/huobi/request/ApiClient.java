package priv.weijianlin.huobi.request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.core.type.TypeReference;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import priv.weijianlin.huobi.model.Account;
import priv.weijianlin.huobi.model.CreateOrderModel;
import priv.weijianlin.huobi.model.Matchresults;
import priv.weijianlin.huobi.model.OrderDetails;
import priv.weijianlin.huobi.model.Symbol;
import priv.weijianlin.huobi.response.ApiException;
import priv.weijianlin.huobi.response.ApiResponse;
import priv.weijianlin.huobi.response.Kline;
import priv.weijianlin.huobi.response.Matchresult;
import priv.weijianlin.huobi.util.AccountInfo;
import priv.weijianlin.huobi.util.ApiSignature;
import priv.weijianlin.huobi.util.JsonUtil;

/**
 * <p>封装API请求</p>
 * @version V1.0, 2018年2月5日
 */
public class ApiClient {
   
    static OkHttpClient client = null;
    
    static final MediaType JSON = MediaType.parse("application/json");
    
    //用国外服务器，用官方客户端，huobi.pro和huobipro.com有一个坏了换另外一个
    static final String API_HOST = "api.huobi.pro";   
    //static final String API_HOST = "api.huobipro.com";

    static final String API_URL = "https://" + API_HOST;
    
    private ApiClient(){}
    
    public static ApiClient getApiClient(OkHttpClient client){
        ApiClient.client = client;
        return new ApiClient();
    } 

    /** 
     * <p>查询当前成交、历史成交</p>
     * <p>功能详细描述</p>
     * @param symbol
     * @param startDate yyyy-mm-dd
     * @param endDate   yyyy-mm-dd
     */
    public List<Matchresults> getMatchresults(Symbol symbol,String startDate,String endDate,
            String types,String direct){
        Map<String, String> params = new HashMap<String,String>();
        params.put("symbol", symbol.getBaseCurrency() + symbol.getQuoteCurrency());
        params.put("start-date", startDate);
        params.put("end-date", endDate);
        params.put("types", types);
        params.put("direct", direct);
        Matchresult resp =
                get("/v1/order/matchresults", params, new TypeReference<Matchresult>() {});
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
     * @param request CreateOrderRequest object.
     * @return Order id.
     */
    public Long createOrder(CreateOrderModel request) {
      ApiResponse<Long> resp =
          post("/v1/order/orders/place", request, new TypeReference<ApiResponse<Long>>() {});
      return resp.checkAndReturn();
    }
    
    /** 
     * <p>行情API</p>
     * <p>获取K线数据</p>
     * @param symbol 交易对
     * @param period K线类型
     * @param size
     * @return
     */
    public List<Kline> getHistoryKline(Symbol symbol,String period,Integer size){
        size = size > 2000 ? 2000 : size; 
        Map<String, String> params = new HashMap<String,String>();
        params.put("symbol", symbol.getBaseCurrency() + symbol.getQuoteCurrency());
        params.put("period", period);
        params.put("size", size.toString());
        ApiResponse<List<Kline>> resp =
                get("/market/history/kline", params, new TypeReference<ApiResponse<List<Kline>>>() {});
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
}
