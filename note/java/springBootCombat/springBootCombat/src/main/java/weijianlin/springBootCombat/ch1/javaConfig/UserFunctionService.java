package weijianlin.springBootCombat.ch1.javaConfig;

//此处没有使用@Service声明bean
public class UserFunctionService {

    //此处没有使用@Autowited注解注入bean
    FunctionService functionService;

    public void setFunctionService(FunctionService functionService){
        this.functionService = functionService;
    }

    public String sayHello(String world){
        return functionService.sayHello(world);
    }
}
