package weijianlin.springBootCombat.ch2.profile;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev"); //先将活动的Profile设置为dev
        context.register(ProfileConfig.class);  //后置注册Bean配置类
        context.refresh();  //刷新容器
        System.out.println(context.getBean(DemoBean.class).getContent());
        context.close();
    }
}
