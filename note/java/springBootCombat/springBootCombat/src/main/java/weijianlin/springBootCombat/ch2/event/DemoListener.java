package weijianlin.springBootCombat.ch2.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

//实现ApplicationListener接口,并指定监听的类型
@Component
public class DemoListener implements ApplicationListener<DemoEvent> {

    @Override       //实现onApplicationEvent对消息进行接收处理
    public void onApplicationEvent(DemoEvent demoEvent) {
        String msg = demoEvent.getMsg();
        System.out.println("我(bean-demoListener)接收到了bean-demoPublisher发布的消息:" + msg);
    }
}
