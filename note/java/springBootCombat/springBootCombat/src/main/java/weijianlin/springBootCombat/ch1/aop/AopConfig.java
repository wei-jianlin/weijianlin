package weijianlin.springBootCombat.ch1.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("weijianlin.springBootCombat.ch1.aop")
//开启spring对AspectJ代理的支持
@EnableAspectJAutoProxy
public class AopConfig {
}
