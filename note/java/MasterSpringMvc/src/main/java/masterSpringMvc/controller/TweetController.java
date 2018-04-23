package masterSpringMvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TweetController {

    @Autowired
    private Twitter twitter;

    @RequestMapping("/")
    //@ResponseBody--移除,将这个字符串映射为视图名
    //通过查看 ThymeleafProperties 类，可以知道视图的默认前缀是“classpath:/templates/”， 后缀是“.html”
    //@RequestParam:默认情况下，请求参数是强制要求存在的,为其指定一个默认值或者将其设置为非必填项
    public String hello(@RequestParam(defaultValue = "masterSpringMvc4") String search ,Model model){
       SearchResults searchResults = twitter.searchOperations().search(search);
        model.addAttribute("message",searchResults.getTweets().get(0).getText());
        return "resultPage";
    }

}
