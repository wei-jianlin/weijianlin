package com.huobi.api.util;

public enum SysmbolStrategyByKlineEnum {

	OMG_USDT(1,"30min",-0.059,0.030),
	ZEC_USDT(1,"15min",-0.020,0.020),
	EOS_USDT(1,"30min",-0.035,0.025),
	LTC_USDT(1,"15min",-0.010,0.010),
	QTUM_USDT(1,"15min",-0.045,0.025),
	BTC_USDT(1,"30min",-0.030,0.010),
	ETC_USDT(2,"30min",-0.030,0.015),
	BCH_USDT(1,"60min",-0.040,0.020),
	DASH_USDT(1,"30min",-0.040,0.015),
	XRP_USDT(1,"15min",-0.035,0.025),
	ETH_USDT(1,"30min",-0.030,0.015),
	HSR_USDT(1,"5min",-0.015,0.015);
	
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