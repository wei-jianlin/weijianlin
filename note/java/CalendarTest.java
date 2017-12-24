package com.huobi.api.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author weijianlin
 * 月份 0 - 11
 * calendar add后, 再用 calendar add 会接着上次操作后的时间继续
 */
public class CalendarTest {

	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.get(calendar.MINUTE));
		Date date = calendar.getTime();
		System.out.println(date.toString());   //Sun Dec 24 11:36:21 CST 2017
		System.out.println(date.toGMTString());//24 Dec 2017 03:36:21 GMT
		System.out.println(date.toLocaleString());//2017-12-24 11:36:21
		calendar.add(Calendar.MINUTE, -30);
		System.out.println(calendar.getTime());
		calendar.add(Calendar.MINUTE, -30);
		System.out.println(calendar.getTime());
	}
}
