package com.huobi.api.request;

import java.util.List;

import com.huobi.api.response.Account;
import com.huobi.api.response.Balance;
import com.huobi.api.util.ApiClient;
import com.huobi.api.util.Arithmetic;
import com.huobi.api.util.SymbolEnum;

public class CreateOrder {

    /** 
     * <p>下单买卖</p>
     * @param client
     * @param price 
     * @param symbol
     * @param type BUY_LIMIT:限价买入,SELL_LIMIT:限价卖出
     * @return 订单ID
     */
    public static Long orderPlace(ApiClient client,String price,SymbolEnum symbol,String type){
        String amount = "";
        String key = symbol.name();
        CreateOrderRequest orderRequest = new CreateOrderRequest(symbol.getValue(),
                Arithmetic.setScale(price,symbol.getPricePrecision()),type);
        //如果是限价卖,则查出拥有symbol中基础货币的数量
        if("sell-limit".equals(type)){
            //数量小数位有限制
            amount = getBalance(client,key.substring(0,key.indexOf("_")).toLowerCase());
            amount = Arithmetic.setScale(amount,symbol.getAmountPrecision());
        }else if("buy-limit".equals(type)){
            //如果是限价买,则查出拥有symbol中计价货币的数量
            String balance = getBalance(client,key.substring(key.indexOf("_") + 1).toLowerCase());
            amount = Arithmetic.divide(balance,price,symbol.getAmountPrecision());
        }
        orderRequest.amount = amount;
        return client.createOrder(orderRequest);
    }
    
    /** 
     * <p>获得交易对的交易余额</p>
     * @param client
     * @param symbol
     * @return
     */
    public static String getBalance(ApiClient client,String symbol){
        Account account = client.getBalance();
        List<Balance> balances = account.list;
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
        String state = client.orderDetail(orderId).state;
        if(!state.equals("canceled")) submitcancel(client,orderId);
        return true;
    }
    
}