package weijianlin.springBootCombat.ch3.aware;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service    //实现BeanNameAware,ResourceLoaderAware获得Bean名称和资源加载服务
public class AwareService implements BeanNameAware,ResourceLoaderAware {

    private String beanName;
    private ResourceLoader loader;

    @Override   //重写setResourceLoader
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.loader = resourceLoader;
    }

    @Override   //重写setBeanName
    public void setBeanName(String s) {
        this.beanName = s;
    }

    public void outputResult(){
        System.out.println("Bean的名字为:" + beanName);
        Resource resource = loader.getResource("classpath:weijianlin/springBootCombat/ch3/aware/test.txt");
        try{
            System.out.print("ResourceLoader加载的文件内容为:" + IOUtils.toString(resource.getInputStream()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
