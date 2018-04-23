package weijianlin.springBootCombat.chl.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFunctionService {

    @Autowired
    private FunctionService functionService;

    public String sayHello(String world){
        return functionService.sayHello(world);
    }
}
