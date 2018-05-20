package weijianlin.springBootCombatSpringMVC.web.ch4_5;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
public class SseController {

    @ResponseBody
    //输出媒体类型为text/event-stream
    @RequestMapping(value="/push",produces = "text/event-stream")
    public String push(){
        Random random = new Random();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  "data:Testing 1,2,3" + random.nextInt() + "\n\n";
    }
}
