package weijianlin.springBootCombatSpringMVC.web.ch4_5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;
import weijianlin.springBootCombatSpringMVC.service.PushService;

@Controller
public class AysncController {

    @Autowired
    private PushService pushService;

    @RequestMapping("/deferr")
    @ResponseBody
    public DeferredResult<String> deferredCall(){
        return pushService.getAsyncUpdate();
    }

}
