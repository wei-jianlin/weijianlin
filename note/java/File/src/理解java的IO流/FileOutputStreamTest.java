package 理解java的IO流;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStreamTest {

    public static void main(String[] args) {
        FileInputStream input = null;
        FileOutputStream output = null;
        String projectPath = System.getProperty("user.dir");
        try {
            input = new FileInputStream(projectPath + "/src/理解java的IO流/FileOutputStreamTest.java");
            output = new FileOutputStream(projectPath + "/newFile");
            byte[] b = new byte[32];
            int hasRead = 0;
            //void write(int c)  将指定的字节输出到输出流中
            //void write(byte[] b)将字节数组中的数据输出到指定输出流中
            //void write(byte b,int off,int len) 将字节数组从off位置开始,取len个字节输出到输出流中
            while((hasRead = input.read(b)) > 0){
                output.write(b,0,hasRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }       
    }

}
