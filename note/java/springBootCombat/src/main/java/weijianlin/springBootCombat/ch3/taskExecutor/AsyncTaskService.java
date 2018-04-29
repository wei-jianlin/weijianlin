package weijianlin.springBootCombat.ch3.taskExecutor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTaskService {

    @Async  //表明该方法是个异步方法,如果注解在类级别,则该类所有的方法都是异步方法
    //而这里的方法自动被注入使用ThreadPoolTaskExecutor做为TaskExecutor
    public void executeAsyncTask(Integer i ){
        System.out.println("执行异步任务:" + i);
    }

    @Async
    public void executeAsyncTaskPlus(Integer i){
        System.out.println("执行异步任务+1:" + (i + 1));
    }
}
