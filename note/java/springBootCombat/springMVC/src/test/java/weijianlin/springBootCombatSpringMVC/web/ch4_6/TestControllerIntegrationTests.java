package weijianlin.springBootCombatSpringMVC.web.ch4_6;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import weijianlin.springBootCombatSpringMVC.MyMvcConfig;
import weijianlin.springBootCombatSpringMVC.service.DemoService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MyMvcConfig.class)
//注解在类上,用来声明加载的ApplicationContext是一个WebApplicationContext,他的属性是web资源的位置,默认为src/main/webapp
@WebAppConfiguration("src/main/resources")
public class TestControllerIntegrationTests {

    private MockMvc mockMvc;    //模拟MVC对象,通过MockMvcBuilders.webAppContextSetup(this.context).build();初始化

    @Autowired
    private DemoService demoService;    //可以在测试用例用注入Bean

    @Autowired
    WebApplicationContext context;  //可注入WebApplicationContext

    @Autowired
    MockHttpSession session;    //注入模拟的http session

    @Autowired
    MockHttpServletRequest request; //注入模拟的httpRequest

    @Before //在测试开始前的初始化动作
    public void setup(){
        //模拟MVC对象,通过MockMvcBuilders.webAppContextSetup(this.context).build();初始化
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void testNormalController() throws Exception{
        mockMvc.perform(get("/normal")) //模拟向.normal进行get请求
        .andExpect(status().isOk())   //预期返回状态为200
        .andExpect(view().name("page"))   //预期view的名称为page
                // 预期页面转向的真正路径为/WEB-INF/classes/views/page.jsp
        .andExpect(forwardedUrl("/WEB-INF/classes/views/page.jsp"))
                //预期model里的值是
            .andExpect(model().attribute("msg",demoService.saySomeThing()));
    }

    @Test
    public void testRestController() throws  Exception{
        mockMvc.perform(get("/testRest")) //模拟向testRest进行get请求
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))//预期返回值的媒体类型为text/plain;charset=UTF-8
                .andExpect(content().string(demoService.saySomeThing()));    //预期返回值的内容为
    }

}
