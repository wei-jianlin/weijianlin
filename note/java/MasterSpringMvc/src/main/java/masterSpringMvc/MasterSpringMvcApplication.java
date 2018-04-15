package masterSpringMvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * @SpringBootApplication注解实际上组合了3个其他的注解:
 * @Configuration:该类处理spring的常规配置.
 * @EnableAutoConfiguration:springBoot自动配置,
 * @ComponentScan:用注解标识的类 会被spring自动扫描并且装入bean容器
 * */
@SpringBootApplication
public class MasterSpringMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterSpringMvcApplication.class, args);
    }
}
