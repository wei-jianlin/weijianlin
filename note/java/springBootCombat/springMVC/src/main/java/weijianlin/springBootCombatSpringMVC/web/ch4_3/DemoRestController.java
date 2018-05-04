package weijianlin.springBootCombatSpringMVC.web.ch4_3;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weijianlin.springBootCombatSpringMVC.domain.DemoObj;

@RestController //声明是控制器,并且返回数据时不需要@ResponseBody
@RequestMapping("/rest")
public class DemoRestController {

    //返回数据的媒体类型为json
    @RequestMapping(value="/getJson",produces = {"application/json;charset=UTF-8"})
    public DemoObj getJson(DemoObj demoObj){
        //直接返回对象,对象会自动转换成json
        return new DemoObj(demoObj.getId() + 1,demoObj.getName() + "yy");
    }

    //返回数据的媒体类型为xml
    @RequestMapping(value="/getXml",produces = {"application/xml;charset=UTF-8"})
    public DemoObj getXml(DemoObj demoObj){
        //直接返回对象,对象会自动转换成xml
        return new DemoObj(demoObj.getId() + 1,demoObj.getName() + "yy");
    }
}
