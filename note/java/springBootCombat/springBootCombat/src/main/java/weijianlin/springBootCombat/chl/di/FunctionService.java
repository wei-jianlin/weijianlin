package weijianlin.springBootCombat.chl.di;

import org.springframework.stereotype.Service;

@Service
public class FunctionService {

    public String sayHello(String world){
        return "hello" + world + "!";
    }
}
