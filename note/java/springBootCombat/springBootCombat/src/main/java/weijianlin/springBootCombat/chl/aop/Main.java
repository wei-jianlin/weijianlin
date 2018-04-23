package weijianlin.springBootCombat.chl.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopConfig.class);
        context.getBean(DemoAnnotationService.class).add();
        context.getBean(DemoMethodService.class).add();
        context.close();
    }
}
