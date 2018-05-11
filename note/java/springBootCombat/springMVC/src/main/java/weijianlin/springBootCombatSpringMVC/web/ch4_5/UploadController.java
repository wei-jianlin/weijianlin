package weijianlin.springBootCombatSpringMVC.web.ch4_5;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadController {

    @ResponseBody
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(MultipartFile file){   //使用MultipartFile接收上传的文件
        try{
            //使用 FileUtils.writeByteArrayToFile快速写文件到磁盘
            FileUtils.writeByteArrayToFile(new File("C:/upload/" + file.getOriginalFilename()),file.getBytes());
            return "ok";
        }catch(IOException e){
            e.printStackTrace();
            return "wrong";
        }
    }
}
