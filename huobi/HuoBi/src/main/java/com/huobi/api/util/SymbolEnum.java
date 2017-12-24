package com.huobi.api.util;

/**
 * <p>交易对</p>
 * <p>功能详细描述</p>
 * @author cz
 * @version V1.0, 2017年12月24日 10:0:0
 * @see 包名.类名#类名
 * @since 2017年12月21日
 * deprecated 如果已经不再推荐使用，请在标识符前加上@符号
 */
public enum SymbolEnum {

    //USDT
    BTC_USDT("btcusdt"),BCH_USDT("bchusdt"),ETH_USDT("ethusdt"),LTC_USDT("ltcusdt"),
    XRP_USDT("xrpusdt"),DASH_USDT("dashusdt"),ETC_USDT("etcusdt"),EOS_USDT("etcusdt"),
    QTUM_USDT("qtumusdt"),ZEC_USDT("zecusdt"),OMG_USDT("omgusdt"),HSR_USDT("hsrusdt"),
    
    
    //BTC
    BCH_BTC("bchbtc"),ETH_BTC("ethbtc"),XRP_BTC("xrpbtc"),LTC_BTC("ltcbtc"),
    DASH_BTC("dashbtc"),ETC_BTC("etcbtc"),EOS_BTC("eosbtc"),OMG_BTC("omgbtc"),
    ZEC_BTC("zecbtc"),BTM_BTC("btmbtc"),ELF_BTC("elfbtc"),HSR_BTC("hsrbtc"),
    VEN_BTC("venbtc"),SALT_BTC("saltbtc"),CVC_BTC("cvcbtc"),MANA_BTC("manabtc"),
    SMT_BTC("smtbtc"),BAT_BTC("batbtc"),QTUM_BTC("qtumbtc"),CMT_BTC("cmtbtc"),
    AST_BTC("astbtc"),WAX_BTC("waxbtc"),MCO_BTC("mcobtc"),ITC_BTC("itcbtc"),
    QASH_BTC("qashbtc"),GNT_BTC("gntbtc"),DGD_BTC("dgdbtc"),PAY_BTC("paybtc"),
    TNB_BTC("tnbbtc"),TNT_BTC("tntbtc"),QSP_BTC("qspbtc"),SNT_BTC("sntbtc"),
    MLT_BTC("mltbtc"),STORJ_BTC("storjbtc"),RDN_BTC("rdnbtc"),RCN_BTC("rcnbtc"),
    KNC_BTC("kncbtc"),ZRX_BTC("zrxbtc"),BTG_BTC("btgbtc"),BCD_BTC("bcdbtc"),
    SBTC_BTC("bcdbtc"),BCX_BTC("bcxbtc"),
    
    
    //ETH
    BTM_ETH("btmeth"),ELF_ETH("elfeth"),HSR_ETH("hsreth"),VEN_ETH("veneth"),
    SALT_ETH("salteth"),CVC_ETH("cvceth"),EOS_ETH("eoseth"),OMG_ETH("omgeth"),
    CMT_ETH("cmteth"),WAX_ETH("waxmeth"),MCO_ETH("mcoeth"),ITC_ETH("itceth"),
    QASH_ETH("qasheth"),GNT_ETH("gnteth"),DGD_ETH("dgdeth"),PAY_ETH("payeth"),
    TNB_ETH("tnbeth"),TNT_ETH("tnteth"),QSP_ETH("qspeth"),RDN_ETH("rdneth"),
    RCN_ETH("rcneth")
    ;
    private String value;
    
    private SymbolEnum(String value){
        this.value = value;
    }
    
    public String getValue(){
        return this.value;
    }
}
