package com.huobi.api.response;

import java.util.List;

/**
 * <p>账户信息</p>
 * <p>功能详细描述</p>
 * @version V1.0, 2017年12月25日
 * @see 包名.类名#类名
 * @since 2017年12月25日
 * deprecated 如果已经不再推荐使用，请在标识符前加上@符号
 */
public class Account {

    public Long id;         //账户ID
    
    public String state;    //账户状态    working：正常, lock：账户被锁定
    
    public String type;     //账户类型   spot：现货账户
    
    //子账户数组
    public List<Balance> list;
}
