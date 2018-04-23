package weijianlin.springBootCombat.chl.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect //声明一个切面
@Component  //注册为springBean
public class LogAspect {

    //声明切点
    @Pointcut("@annotation(weijianlin.springBootCombat.chl.aop.Action)")
    public void annotationPointCut(){}

    //声明一个建言,并使用@Poincut定义的切点
    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint){
        //通过反射获得注解上的属性
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        Action action = method.getAnnotation(Action.class);
        System.out.println("注解式拦截:" + action.name());
    }

    //声明一个建言,直接使用拦截规则作为参数
    @Before("execution(* weijianlin.springBootCombat.chl.aop.DemoMethodService.* (..))")
    public void before(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.print("方法规则式拦截:" + method.getName());
    }
}
