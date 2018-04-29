package weijianlin.springBootCombat.ch2.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EventConfig.class);
        context.getBean(DemoPublisher.class).publish("hello application event");
        context.close();
    }
}
