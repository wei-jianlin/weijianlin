package weijianlin.springBootCombatSpringMVC.domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller //1
@RequestMapping("/anno")    //2
public class DemoAnnoController {

    //3
    @RequestMapping(produces = "text/plain;charset=UTF-8")
    //4
    public @ResponseBody String index(HttpServletRequest request){
        return "url:" + request.getRequestURL() + "can access";
    }

    //5
    @RequestMapping(value="/pathvar/{str}",produces = "text/plain;charset=UTF-8")
    public String demoPathVar(@PathVariable String str, HttpServletRequest request){
        return "url" + request.getRequestURL() + "can access;str:" + str;
    }
}
