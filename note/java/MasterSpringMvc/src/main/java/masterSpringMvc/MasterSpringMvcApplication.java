package masterSpringMvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*
 * @SpringBootApplication注解实际上组合了3个其他的注解:
 * @Configuration:该类处理spring的常规配置.
 * @EnableAutoConfiguration:springBoot自动配置,
 * @ComponentScan:用注解标识的类 会被spring自动扫描并且装入bean容器,
 * 默认情况下，这个注解将会扫描当前包以及该包下面的所有子包
 * */
@SpringBootApplication
@ComponentScan("masterSpringMvc")
public class MasterSpringMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterSpringMvcApplication.class, args);
    }
}
