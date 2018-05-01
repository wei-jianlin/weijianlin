package weijianlin.springBootCombatSpringMVC.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller //声明是控制器
public class HelloController {

    @RequestMapping("/index") //配置URL和方法之间的映射
    public String hello(){
        return "index"; //通过之前viewResolver的配置,说明我们的页面防止路径为/WEB-INF/classes.views/index.jsp
    }
}
