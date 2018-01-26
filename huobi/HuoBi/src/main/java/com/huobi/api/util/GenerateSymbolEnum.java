package com.huobi.api.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.huobi.api.response.Symbol;

/**
 * <p>生成SymbolEnum类</p>
 * <p>功能详细描述</p>
 * @author cz
 * @version V1.0, 2018年1月23日
 * @see 包名.类名#类名
 * @since 2018年1月23日
 * deprecated 如果已经不再推荐使用，请在标识符前加上@符号
 */
public class GenerateSymbolEnum {

    public static void main(String[] args) {
        ApiClient client = new ApiClient();
        StringBuilder str = new StringBuilder("package com.huobi.api.util;\r\n\r\npublic enum SymbolEnum {\r\n\r\n");
        str = getSymbols(client,str);
        str = classEnd(str);
        byte[] bytes = str.toString().getBytes();
        String projectPath = System.getProperty("user.dir");
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(projectPath + "/src/main/java/com/huobi/api/util/SymbolEnum.java");
            output.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(output != null){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** 
     * <p>查询交易对</p>
     * <p>功能详细描述</p>
     * @param client
     */
    public static StringBuilder getSymbols(ApiClient client,StringBuilder str){
        str.append("\t");
        List<Symbol> list = client.getSymbols();
        for(int i = 0; i < list.size(); i++){
            Symbol symbol = list.get(i);
            str.append(symbol.baseCurrency.toUpperCase()) 
                    .append("_" + symbol.quoteCurrency.toUpperCase())
                    .append("(\"" + symbol.baseCurrency + symbol.quoteCurrency + "\",")
                    .append("\"" + symbol.pricePrecision + "\",")
                    .append("\"" + symbol.amountPrecision + "\")");
            if(i != list.size() - 1){
                str.append(","); 
            }else{
                str.append(";");
            }
            if((i + 1) % 3 == 0 && i != 0){
                str.append("\r\n\t");
            }
        }
        return str;
    }
    
    public static StringBuilder classEnd(StringBuilder str){
        str.append("\r\n\r\n\tprivate String value;               //交易对");
        str.append("\r\n\tprivate String pricePrecision;      //价格精度");
        str.append("\r\n\tprivate String amountPrecision;     //数量精度");
        str.append("\r\n\r\n\tprivate SymbolEnum(String value,String pricePrecision,String amountPrecision){");
        str.append("\r\n\t\tthis.value = value;");
        str.append("\r\n\t\tthis.pricePrecision = pricePrecision;");
        str.append("\r\n\t\tthis.amountPrecision = amountPrecision;\r\n\t}");
        str.append("\r\n\r\n\tpublic String getValue(){\r\n\t\treturn this.value;\r\n\t}");
        str.append("\r\n\r\n\tpublic String getPricePrecision(){\r\n\t\treturn this.pricePrecision;\r\n\t}");
        str.append("\r\n\r\n\tpublic String getAmountPrecision(){\r\n\t\treturn this.amountPrecision;\r\n\t}");
        str.append("\r\n}");
        return str;
    }
}
