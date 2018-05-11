package weijianlin.springBootCombatSpringMVC.messageconverter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;
import weijianlin.springBootCombatSpringMVC.domain.DemoObj;

import java.io.IOException;
import java.nio.charset.Charset;

//继承AbstractHttpMessageConverter实现自定义HttpMessageConverter
//HttpMessageConverter是用来处理request和response里的数据的
public class MyMessageConverter extends AbstractHttpMessageConverter<DemoObj> {

    public MyMessageConverter() {
        //新建一个我们自定义的媒体类型
        super(new MediaType("application","x-wisely",Charset.forName("UTF-8")));
    }

    /**
     * 表明本HttpMessageConverter只处理DemoObj这个类
     */
    @Override
    protected boolean supports(Class<?> aClass) {
        return DemoObj.class.isAssignableFrom(aClass);
    }

    /**
     * 重写readInternal方法,处理请求数据
     */
    @Override
    protected DemoObj readInternal(Class<? extends DemoObj> aClass,
                                   HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        String temp = StreamUtils.copyToString(httpInputMessage.getBody(), Charset.forName("UTF-8"));
        String[] tempArr = temp.split("-");
        return new DemoObj(new Long(tempArr[0]),tempArr[1]);
    }

    /**
     * 重写writeInternal,输出数据到response中
     */
    @Override
    protected void writeInternal(DemoObj demoObj, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        String out = "hello:" + demoObj.getId() + "-" +demoObj.getName();
        httpOutputMessage.getBody().write(out.getBytes());
    }
}
