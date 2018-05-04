package weijianlin.springBootCombatSpringMVC;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import weijianlin.springBootCombatSpringMVC.interceptor.DemoInterceptor;

@Configuration
@EnableWebMvc   //开启springMVC支持,若无此句,重写WebMvcConfigurerAdapter方法无效
@ComponentScan("weijianlin.springBootCombatSpringMVC")
//继承WebMvcConfigurerAdapter类,重写其方法可对Spring MVC进行配置
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/classes/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceLocations指的是文件放置的目录,addResourceHandler指的是对外暴露的访问路径
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
    }

    @Bean   //配置拦截器的Bean
    public DemoInterceptor demoInterceptor(){
        return new DemoInterceptor();
    }

    @Override   //注册拦截器
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(demoInterceptor());
    }

    @Override   //页面转向,访问/index,跳转到index.jsp
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/toUpload").setViewName("upload");
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //不忽略url中.后面的参数
        configurer.setUseSuffixPatternMatch(false);
    }

    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(1000000);
        return resolver;
    }
}
