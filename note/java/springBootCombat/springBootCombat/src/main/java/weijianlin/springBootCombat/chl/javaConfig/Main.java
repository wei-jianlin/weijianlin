package weijianlin.springBootCombat.chl.javaConfig;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        //使用AnnotationConfigApplicationContext作为spring容器,接受输入一个配置类作为参数
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
        UserFunctionService uerFunctionService = context.getBean(UserFunctionService.class);
        System.out.print(uerFunctionService.sayHello("javaConfig"));
        context.close();
    }
}
