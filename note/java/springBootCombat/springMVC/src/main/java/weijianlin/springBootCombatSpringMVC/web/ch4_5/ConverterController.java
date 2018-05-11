package weijianlin.springBootCombatSpringMVC.web.ch4_5;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weijianlin.springBootCombatSpringMVC.domain.DemoObj;

@RestController
public class ConverterController {

    //指定返回类型为我们自定义的application/x-wisely
    @RequestMapping(value="/convert",produces = {"application/x-wisely"})
    public DemoObj convert(DemoObj demoObj){
        return demoObj;
    }
}
