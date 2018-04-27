package weijianlin.springBootCombat.ch2.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DemoPublisher {

    @Autowired  //注入applicationContext用来发布事件
    ApplicationContext applicationContext;

    public void publish(String msg){
        //使用publishEvent方法来发布
        applicationContext.publishEvent(new DemoEvent(this,msg));
    }
}
