package masterSpringMvc.controller;

@Controller
public class HelloController {

    @RequestMapping("/")
    @RequestBody
    public String hello(){
        return "hello world";
    }
}
