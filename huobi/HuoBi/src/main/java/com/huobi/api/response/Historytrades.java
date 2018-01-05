package com.huobi.api.response;

import java.util.List;

import com.huobi.api.ApiException;

public class Historytrades {
    
    public String status;       //ok,error
    public String ch;           //数据所属的 channel，格式： market.$symbol.trade.detail
    public Long ts;           //发送时间
    public List<Historytrade> data;

    public List<Historytrade> checkAndReturn() {
      if ("ok".equals(status)) {
        return data;
      }
      throw new ApiException("","");
    }
}
