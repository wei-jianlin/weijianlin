package 理解java的IO流;

import java.io.FileInputStream;
import java.io.IOException;

public class FileInputStreamTest {

    public static void main(String[] args){
        FileInputStream input = null;
        String projectPath = System.getProperty("user.dir");
        try {
            input = new FileInputStream(projectPath + "/src/理解java的IO流/FileInputStreamTest.java");
            //创建一个长度为1024的竹筒
            byte[] bytes = new byte[1024];
            int hasRead = 0;
            //int read():从输入流读取单个字节,返回所读取的字节数据(字节数据可直接转换为int类型)
            //int read(byte[] b)从输入流中读取b.length个字节的数据,并保存在字节数组b中,返回实际读取的字节数
            //int read(byte[] b,int off,int len):从输入流中最多读取len长度的字节数据,并将其存储在数组b中,
            //放入b数组中时,并不是从数组起点开始,而是从off位置开始,返回实际读取的字节数
            while((hasRead = input.read(bytes)) > 0){
                //通过使用平台的默认字符集解码指定的字节子阵列来构造新的String 
                //bytes - 要解码为字符的字节  offset - 要解码的第一个字节的索引 length - 要解码的字节数 
                System.out.println(new String(bytes,0,hasRead));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
