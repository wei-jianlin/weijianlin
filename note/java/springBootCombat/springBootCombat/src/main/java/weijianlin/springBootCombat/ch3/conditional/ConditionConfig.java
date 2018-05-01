package weijianlin.springBootCombat.ch3.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConditionConfig {

    @Bean
    @Conditional(WindowsCondition.class)    //@Conditional条件注解,符合windows条件则实例化WindowsListService
    public ListService windowsListService(){
        return new WindowsListService();
    }

    @Bean
    @Conditional(LinuxCondition.class)  //@Conditional条件注解,符合wlinux条件则实例化WindowsListService
    public ListService linuxListService(){
        return new LinuxListService();
    }
}
