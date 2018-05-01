package weijianlin.springBootCombat.ch3.conditional;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConditionConfig.class);
        System.out.println(context.getEnvironment().getProperty("os.name") + "系统下的列表命令为:" +
                context.getBean(ListService.class).showListCmd());
        context.close();
    }
}
