package weijianlin.springBootCombatSpringMVC.web.ch4_6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import weijianlin.springBootCombatSpringMVC.service.DemoService;

@Controller
public class RestController {

    @Autowired
    DemoService demoService;

    @ResponseBody
    @RequestMapping(value="/testRest",produces = "text/plain;charset=UTF-8")
    public String testRest(){
        return demoService.saySomeThing();
    }
}
