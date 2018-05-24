package weijianlin.SpringBoot.ch_5_2_3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication  //是springBoot的核心注解,将在6.1.2节做更详细的讲解
public class Ch523Application {

    @Value("${book.author}")
    private String bookAuthor;

    @Value("${book.name}")
    private String bookName;

    @RequestMapping("/")
    String index(){
        return "book name is:" + bookName + " and book author is " + bookAuthor;
    }

    public static void main(String[] args) {
        //将在6.1.1节做更详细的讲解
        SpringApplication.run(Ch523Application.class, args);
    }
}
