package com.huobi.api.util;

/**
 * <p>交易对</p>
 * <p>不买卖分叉币</p>
 * @version V1.0, 2017年12月24日 10:00:0
 * @see 包名.类名#类名
 * @since 2017年12月21日
 * deprecated 如果已经不再推荐使用，请在标识符前加上@符号
 */
public enum SymbolEnum {

    //USDT
    BTC_USDT("btcusdt","2","4"),BCH_USDT("bchusdt","2","4"),ETH_USDT("ethusdt","2","4"),
    LTC_USDT("ltcusdt","2","4"),XRP_USDT("xrpusdt","4","0"),DASH_USDT("dashusdt","2","4"),
    ETC_USDT("etcusdt","2","4"),EOS_USDT("eosusdt","2","4"),QTUM_USDT("qtumusdt","2","4"),
    ZEC_USDT("zecusdt","2","4"),OMG_USDT("omgusdt","2","4"),HSR_USDT("hsrusdt","2","4"),
   
    
    //BTC
    BCH_BTC("bchbtc","6","4"),ETH_BTC("ethbtc","6","4"),XRP_BTC("xrpbtc","8","0"),
    LTC_BTC("ltcbtc","6","4"),DASH_BTC("dashbtc","6","4"),ETC_BTC("etcbtc","6","4"),
    EOS_BTC("eosbtc","8","2"),OMG_BTC("omgbtc","6","4"),ZEC_BTC("zecbtc","6","4"),
    BTM_BTC("btmbtc","8","0"),ELF_BTC("elfbtc","8","0"),HSR_BTC("hsrbtc","6","4"),
    VEN_BTC("venbtc","8","2"),SALT_BTC("saltbtc","6","4"),CVC_BTC("cvcbtc","8","0"),
    MANA_BTC("manabtc","8","0"),SMT_BTC("smtbtc","8","0"),BAT_BTC("batbtc","8","0"),
    QTUM_BTC("qtumbtc","6","4"),CMT_BTC("cmtbtc","8","0"),AST_BTC("astbtc","8","0"),
    WAX_BTC("waxbtc","8","4"),MCO_BTC("mcobtc","6","4"),ITC_BTC("itcbtc","8","0"),
    QASH_BTC("qashbtc","8","4"),GNT_BTC("gntbtc","8","4"),DGD_BTC("dgdbtc","6","4"),
    PAY_BTC("paybtc","6","4"),TNB_BTC("tnbbtc","8","0"),TNT_BTC("tntbtc","8","0"),
    QSP_BTC("qspbtc","8","0"),SNT_BTC("sntbtc","8","0"),//MLT_BTC("mltbtc","6","4"),
    STORJ_BTC("storjbtc","8","2"),RDN_BTC("rdnbtc","8","0"),RCN_BTC("rcnbtc","8","0"),
    KNC_BTC("kncbtc","8","0"),ZRX_BTC("zrxbtc","8","0"),
    
    
    //ETH
    BTM_ETH("btmeth","8","0"),ELF_ETH("elfeth","8","0"),HSR_ETH("hsreth","6","4"),
    VEN_ETH("veneth","8","2"),SALT_ETH("salteth","6","4"),CVC_ETH("cvceth","8","0"),
    EOS_ETH("eoseth","8","2"),OMG_ETH("omgeth","6","4"),CMT_ETH("cmteth","8","0"),
    WAX_ETH("waxeth","6","4"),MCO_ETH("mcoeth","6","4"),ITC_ETH("itceth","8","0"),
    QASH_ETH("qasheth","6","4"),GNT_ETH("gnteth","8","0"),DGD_ETH("dgdeth","6","4"),
    PAY_ETH("payeth","6","4"),TNB_ETH("tnbeth","8","0"),TNT_ETH("tnteth","8","0"),
    QSP_ETH("qspeth","8","0"),RDN_ETH("rdneth","8","0"),RCN_ETH("rcneth","8","0")
    ;
    
    private String value;               //交易对
    private String pricePrecision;      //价格精度
    private String amountPrecision;     //数量精度
    
    private SymbolEnum(String value,String pricePrecision,String amountPrecision){
        this.value = value;
        this.pricePrecision = pricePrecision;
        this.amountPrecision = amountPrecision;
    }
    
    public String getValue(){
        return this.value;
    }
    
    public String getPricePrecision(){
        return this.pricePrecision;
    }
    
    public String getAmountPrecision(){
        return this.amountPrecision;
    }
}
