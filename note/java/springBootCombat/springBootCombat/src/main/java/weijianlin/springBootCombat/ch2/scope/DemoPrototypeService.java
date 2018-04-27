package weijianlin.springBootCombat.ch2.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
//声明Scope为prototype
@Scope("prototype")
public class DemoPrototypeService {
}
