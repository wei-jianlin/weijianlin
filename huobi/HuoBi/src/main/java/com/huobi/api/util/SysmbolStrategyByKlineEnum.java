package com.huobi.api.util;

public enum SysmbolStrategyByKlineEnum {

	OMG_USDT(1,"30min",-0.059,0.030),
	ZEC_USDT(1,"1min",-0.010,0.015),
	SNT_USDT(1,"1min",-0.010,0.010),
	EOS_USDT(1,"30min",-0.069,0.015),
	LTC_USDT(1,"1min",-0.005,0.015),
	QTUM_USDT(1,"5min",-0.025,0.015),
	NEO_USDT(1,"30min",-0.035,0.020),
	BTC_USDT(1,"60min",-0.049,0.020),
	ETC_USDT(1,"30min",-0.059,0.015),
	BCH_USDT(1,"60min",-0.035,0.005),
	DASH_USDT(1,"30min",-0.035,0.010),
	XRP_USDT(1,"60min",-0.064,0.020),
	ETH_USDT(1,"5min",-0.020,0.010),
	HSR_USDT(1,"1min",-0.010,0.005);
	
	private int root;             //几根
	private String period;        //K线类别
	private double buyRise;       //买入位置
	private double saleRise;      //卖出位置

	private SysmbolStrategyByKlineEnum(int root,String period,double buyRise,double saleRise){
		this.root = root;
		this.period = period;
		this.buyRise = buyRise;
		this.saleRise = saleRise;
	}

	public int getRoot(){
		return this.root;
	}

	public String getPeriod(){
		return this.period;
	}

	public double getBuyRise(){
		return this.buyRise;
	}

	public double getSaleRise(){
		return this.saleRise;
	}
}