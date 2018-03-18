package priv.weijianlin.huobi.request;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import priv.weijianlin.huobi.model.Account;
import priv.weijianlin.huobi.model.Balance;
import priv.weijianlin.huobi.model.CreateOrderModel;
import priv.weijianlin.huobi.model.Matchresults;
import priv.weijianlin.huobi.model.Symbol;
import priv.weijianlin.huobi.response.ApiException;
import priv.weijianlin.huobi.util.Arithmetic;

@SuppressWarnings("deprecation")
public class CreateOrderRequest {

    private static final Logger logger = LoggerFactory.getLogger(CreateOrderRequest.class);
    
    /** 
     * <p>下单买卖</p>
     * @param client
     * @param price 
     * @param symbol
     * @param type BUY_LIMIT:限价买入,SELL_LIMIT:限价卖出
     * @return 订单ID
     */
    public static Long orderPlace(ApiClient client,String price,Symbol symbol,String type){
        String amount = "";
        String balance = "";
        CreateOrderModel orderRequest = new CreateOrderModel(symbol.getBaseCurrency() + symbol.getQuoteCurrency(),
                Arithmetic.setScale(price,symbol.getPricePrecision()),type);
        //如果是限价卖,则查出拥有symbol中基础货币的数量
        if("sell-limit".equals(type)){
            //数量小数位有限制
            amount = getBalance(client,symbol.getBaseCurrency());
            amount = Arithmetic.setScale(amount,symbol.getAmountPrecision());
        }else if("buy-limit".equals(type)){
            //如果是限价买,则查出拥有symbol中计价货币的数量
            balance = getBalance(client,symbol.getQuoteCurrency());
            amount = Arithmetic.divide(balance,price,symbol.getAmountPrecision());
        }else if("buy-market".equals(type)) {
            //市价买,则查出拥有symbol中计价货币的数量
            balance = getBalance(client,symbol.getQuoteCurrency());         
            amount = Arithmetic.setScale(balance,symbol.getPricePrecision());
            orderRequest.setPrice("0.0");
        }else if("sell-market".equals(type)){
            //市价卖,则查出拥有symbol中基础货币的数量
            balance = getBalance(client,symbol.getBaseCurrency());         
            amount = Arithmetic.setScale(balance,symbol.getPricePrecision());
            orderRequest.setPrice("0.0");
        }
        orderRequest.setAmount(amount);
        logger.info("计价货币" + symbol.getQuoteCurrency() +"的数量:" + balance + ",买入"
        			+ symbol.getBaseCurrency()+"数量:" + amount + ",type:" + type  
        			+ ",精度:" + symbol.getPricePrecision());
        Long orderId = null;
        try {
            orderId = client.createOrder(orderRequest);
        }catch (ApiException e) {
            orderId = null;
            logger.error("API Error! err-code: " + e.getErrCode() + ", err-msg: " + e.getMessage()
            + ",paramter:" + symbol.getBaseCurrency());
        }       
        return orderId;
    }
    
    /** 
     * <p>获得交易对的交易余额</p>
     * @param client
     * @param symbol
     * @return
     */
    public static String getBalance(ApiClient client,String symbol){
        Account account = client.getBalance();
        List<Balance> balances = account.getList();
        for(Balance balance :balances){
            if(balance.getCurrency().equals(symbol) && balance.getType().equals("trade")){
                return balance.getBalance();
            }
        }
        return "";
    }
    
    /** 
     * <p>撤销订单</p>
     * <p>直到撤销订单为止</p>
     * @param client
     * @param orderId
     * @return
     */
    public static boolean submitcancel(ApiClient client,String orderId){
        client.submitcancel(orderId);
        String state = client.orderDetail(orderId).getState();
        if(!state.equals("canceled")) submitcancel(client,orderId);
        return true;
    }
    
    /** 
     * <p>查询当前成交、历史成交</p>
     * <p>功能详细描述</p>
     * @param client
     * @param startDate  yyyy-mm-dd
     * @param endDate    yyyy-mm-dd
     */
    public static void getMatchresults(ApiClient client,Symbol symbol,String startDate,
                    String endDate){
        List<Matchresults> list = client.getMatchresults(symbol, startDate, endDate,"","");
        for(Matchresults matchresult : list){
           logger.info(symbol.getBaseCurrency() + symbol.getQuoteCurrency() + ",成交数量:" + matchresult.getFilledAmount()
                    + ",成交手续费:" + matchresult.getFilledFees() + ",成交价格:" + matchresult.getPrice()
                    + ",成交方向:" + matchresult.getType() + ",时间:" 
                    + new Date(matchresult.getCreatedAt()).toLocaleString());
        }
    }
}
