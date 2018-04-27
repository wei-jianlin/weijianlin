package weijianlin.springBootCombat.ch1.javaConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  //表明当前类是一个配置类,可能有零个或多个@Bean注解
public class JavaConfig {

    @Bean   //使用@Bean注解声明当前方法FunctionService的返回值是一个Bean,Bean的名称是方法名
    public FunctionService functionService(){
        return new FunctionService();
    }

/*    @Bean
    public UserFunctionService userFunctionService(){
        UserFunctionService userFunctionService = new UserFunctionService();
        userFunctionService.setFunctionService(functionService());
        return userFunctionService;
    }*/

    @Bean   //另一种注入的方式,直接将FunctionService作为参数给userFunctionService(),
    // 在spring容器中,只要容器中存在某个Bean,就可以在另一个Bean的声明方法中使用
    public UserFunctionService userFunctionService(FunctionService functionService){
        UserFunctionService userFunctionService = new UserFunctionService();
        userFunctionService.setFunctionService(functionService);
        return userFunctionService;
    }
}
