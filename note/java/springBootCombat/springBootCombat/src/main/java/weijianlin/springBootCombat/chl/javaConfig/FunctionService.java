package weijianlin.springBootCombat.chl.javaConfig;

//没有使用@service声明bean
public class FunctionService {

    public String sayHello(String world){
        return "hello" + world + "";
    }
}
