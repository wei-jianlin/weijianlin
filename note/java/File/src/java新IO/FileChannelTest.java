package java新IO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class FileChannelTest {
    //Channel可以直接将文件的部分或全部直接映射成Buffer
    //程序不能直接访问Channel中的数据,包括读取写入都不行,Channel只能与Buffer进行交互.
    public static void main(String[] args) throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        String projectPath = System.getProperty("user.dir");
        try{
            File file = new File(projectPath + "/src/java新IO/FileChannelTest.java");
            //创建FileInputStream,以该文件输入流创建FileChannel
            inChannel = new FileInputStream(file).getChannel();
            //将FileChannel里的数据全部映射成ByteBuffer
            //MappedByteBuffer map(FileChannel.MapMode mode,long position,long size)
            MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            //使用UTF-8字符来创建解码器
            Charset charset = Charset.forName("UTF-8");
            outChannel = new FileOutputStream(projectPath + "/FileChannelTest").getChannel();
            outChannel.write(buffer);
            buffer.clear();
            CharBuffer charBuffer = charset.decode(buffer);
            System.out.println(charBuffer);
        }finally{
            if(inChannel != null){
                inChannel.close();
            }
            if(outChannel != null){
                outChannel.close();
            }        
        }
    }

}
