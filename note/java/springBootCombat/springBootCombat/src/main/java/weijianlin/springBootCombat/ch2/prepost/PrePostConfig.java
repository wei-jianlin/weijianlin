package weijianlin.springBootCombat.ch2.prepost;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("weijianlin.springBootCombat.ch2.prepost")
public class PrePostConfig {

    //initMethod和destroyMethod指定BeanWayService类的init和destroy方法在构造之后和销毁之前执行
    @Bean(initMethod = "init",destroyMethod = "destroy")
    BeanWayService beanWayService(){
        return new BeanWayService();
    }

    @Bean
    JSR250WayService jsr250WayService(){
        return new JSR250WayService();
    }
}
