package priv.weijianlin.huobi.enums;

import java.util.Calendar;

public enum PeriodEnum {
    DAY_1("1day"),MIN_1("1min"), MIN_5("5min"), MIN_15("15min"),MIN_30("30min"),
    MIN_60("60min"),WEEK_1("1week")/*,MON_1("1mon"),YEAR_1("1year")*/;
    
    private String value;
    
    private PeriodEnum(String value){
        this.value = value;
    }
    
    public String getValue(){
        return this.value;
    }
    
    public static int getRoot(int day,PeriodEnum period){
        int theDayOfMin = 24 * 60; 
        switch(period.getValue()){
        case "1min":
            return day * theDayOfMin;
        case "5min":
            return day * theDayOfMin / 5;
        case "15min":
            return day * theDayOfMin / 15;
        case "30min":
            return day * theDayOfMin / 30; 
        case "60min":
            return day * theDayOfMin / 60; 
        case "1day":
            return day; 
        case "1week":
            return day / 7;      
        }
        return 0;
    }
    
    public static Calendar getKlineTime(String period){
        Calendar calendar = Calendar.getInstance();
        int miute = 0;
        switch(period){
        case "5min":
            miute = calendar.get(Calendar.MINUTE) % 5;
            break;
        case "15min":
            miute = calendar.get(Calendar.MINUTE) % 15;
            break;
        case "30min":
            miute = calendar.get(Calendar.MINUTE) % 30;
            break;
        case "60min":
            miute = calendar.get(Calendar.MINUTE) % 60;  
            break;
        }
        calendar.add(Calendar.MINUTE, -miute);
        return calendar;
    }
}
