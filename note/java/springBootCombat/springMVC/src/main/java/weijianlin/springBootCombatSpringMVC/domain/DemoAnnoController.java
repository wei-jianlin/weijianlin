package weijianlin.springBootCombatSpringMVC.domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller //声明此类是一个控制器
@RequestMapping("/anno")    //映射此类的访问路径是/anno
public class DemoAnnoController {

    //此方法未标注路径,因此使用类级别的路径/anno,produces可定制返回的response媒体类型和字符集,
    //或需返回值是json对象,则设置produces="application/json;charset=UTF-8"
    @RequestMapping(produces = "text/plain;charset=UTF-8")
    //@ResponseBody用在返回值前面
    public @ResponseBody String index(HttpServletRequest request){
        return "url:" + request.getRequestURL() + " can access";
    }

    //接受路径参数,并在方法参数前结合@PathVariable使用,访问路径为/anno/pathvar/xx
    @ResponseBody
    @RequestMapping(value="/pathvar/{str}",produces = "text/plain;charset=UTF-8")
    public String demoPathVar(@PathVariable String str, HttpServletRequest request){
        return "url" + request.getRequestURL() + "can access;str:" + str;
    }

    //演示常规的request参数获取,访问路径为/anno/requestParam?id=1
    @RequestMapping(value="/requestParam",produces="text/plain;charset=UTF-8")
    public @ResponseBody String passRequestParam(Long id,HttpServletRequest request){
        return "url:" + request.getRequestURL() + "can access,id:" + id;
    }

    @ResponseBody   //ResponseBody也可以放在方法上
    //演示解析参数到对象,访问路径为/anno/obj?id=1&name=xx
    @RequestMapping(value="/obj",produces = "application/json;charset=UTF-8")
    public String passObj(DemoObj demoObj,HttpServletRequest request){
        return "url:" + request.getRequestURL() + " can access,obj.id:" + demoObj.getId() + ",obj name:" + demoObj.getName();
    }

    //映射不同的路径到相同的方法,访问路径为/anno/name1或/anno/name2
    @RequestMapping(value={"/name1","/name2"},produces = "text/plain;charset=UTF-8")
    public @ResponseBody String remove(HttpServletRequest request){
        return "url:" + request.getRequestURL() + " can access";
    }
}
