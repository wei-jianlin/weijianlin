package java新IO;

import java.io.FileOutputStream;
import java.io.IOException;

public class Test {

    public static void main(String[] args) {
        String content = "1234";
        String projectPath = System.getProperty("user.dir");
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(projectPath + "/test");
            byte[] bytes = content.getBytes();
            output.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(output != null){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
