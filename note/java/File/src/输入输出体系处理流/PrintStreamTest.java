package 输入输出体系处理流;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class PrintStreamTest {

    public static void main(String[] args) {
        PrintStream print = null;
        String projectPath = System.getProperty("user.dir");
        try {
            FileOutputStream output  = new FileOutputStream(projectPath + "/PrintStreamTest");
            //以PrintStream来包装FileOutputStream输出流
            print = new PrintStream(output);
            //输出普通字符串和对象
            print.println("普通字符串");
            print.println(new PrintStreamTest());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //当我们使用处理流来包装底层节点流之后,关闭输入输出流资源时,只要关闭最上层的处理流即可.
            print.close();
        }
    }

}
