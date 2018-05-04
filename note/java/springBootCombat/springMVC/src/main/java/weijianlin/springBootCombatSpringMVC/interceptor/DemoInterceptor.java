package weijianlin.springBootCombatSpringMVC.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//继承HandlerInterceptorAdapter实现自定义拦截器,也可以实现HandlerInterceptor接口
public class DemoInterceptor extends HandlerInterceptorAdapter {

    @Override
    //在请求发生前执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime",startTime);
        return true;
    }

    @Override
    //在请求执行后执行
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long)request.getAttribute("startTime");
        request.removeAttribute("startTime");
        long endTime = System.currentTimeMillis();
        System.out.println("本次请求处理时间:" + new Long(endTime - startTime) + "ms");
        request.setAttribute("handleTime",endTime - startTime);
    }
}
