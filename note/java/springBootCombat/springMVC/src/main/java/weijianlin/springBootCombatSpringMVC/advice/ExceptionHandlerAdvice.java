package weijianlin.springBootCombatSpringMVC.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice   //声明一个控制器建言,@ControllerAdvice组合了@Component注解,自动注册为spring的Bean
public class ExceptionHandlerAdvice {

    //@ExceptionHandler在此处定义全局处理,通过value属性可过滤拦截条件
    @ExceptionHandler(value=Exception.class)
    public ModelAndView exception(Exception e, WebRequest request){
        ModelAndView modelAndView = new ModelAndView("error");  //error页面
        modelAndView.addObject("errorMessage",e.getMessage());
        return modelAndView;
    }

    @ModelAttribute //@ModelAttribute将键值对添加到全局,所有注解了@RequestMapping的方法可以获得此键值对
    public void addAttributes(Model model){
        model.addAttribute("msg","额外信息");
    }

    @InitBinder //通过@InitBinder注解定制webDataBinder
    public void initBinder(WebDataBinder webDataBinder){
        //WebDataBinder用来自动绑定前台请求参数到model中,此处演示忽略request参数的id
        webDataBinder.setDisallowedFields("id");
    }

}
