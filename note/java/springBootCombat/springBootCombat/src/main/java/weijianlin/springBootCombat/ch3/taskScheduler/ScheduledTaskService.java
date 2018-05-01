package weijianlin.springBootCombat.ch3.taskScheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ScheduledTaskService {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)    //通过@Scheduled声明该方法是计划任务,使用fixdRate属性每隔固定时间执行
    public void reportCurrentTime(){
        System.out.println("每五秒执行一次" + SIMPLE_DATE_FORMAT.format(new Date()));
    }

    @Scheduled(cron = "0 55 17 ? * *")   //使用cron属性可按照指定时间执行,本例指的是每天17点55分执行
    public void fixTimeExecution(){
        System.out.println("在指定时间" + SIMPLE_DATE_FORMAT.format(new Date()) + "执行");
    }
}
