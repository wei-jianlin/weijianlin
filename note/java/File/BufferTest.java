import java.nio.CharBuffer;

public class BufferTest {

    public static void main(String[] args) {
        //创建一个容量为capacity 的 Xxxbuffer对象.
        CharBuffer buffer = CharBuffer.allocate(8);
        System.out.println(buffer.capacity());//最大数据容量,创建后不能改变
        System.out.println(buffer.limit());//位于limit及后的数据既不可以被读,也不可以被写
        //用于指明下一个可以被读出的或者写入的缓冲区位置索引,每放入一些数据,buffer的position相应地向后移动一些位置
        System.out.println(buffer.position());
        buffer.put('a');
        buffer.put('b');
        buffer.put('c');
        System.out.println("加入三个元素后,position的位置:" + buffer.position());//3
        //为输出数据做准备:当buffer装入数据结束后,调用flip方法,该方法将limit设置为position位置,将position置为0,
        //这样使得从buffer读数据时总从0开始,读完刚刚装入的所有数据结束.
        buffer.flip();
        System.out.println("执行flip后,limit的位置:"+buffer.limit());//3
        System.out.println("执行flip后,position的位置:"+buffer.position());//0
        System.out.println("取出第一个元素:" + buffer.get());//a
        System.out.println("取出一个元素后,position=" + buffer.position());//1
        //为装入数据做好准备:当buffer输出数据结束后,clear方法将position置为0,limit置为capacity
        buffer.clear();
        System.out.println("执行clear后,limit的位置:"+buffer.limit());//8
        System.out.println("执行clear后,position的位置:"+buffer.position());//0
        System.out.println("执行clear后,buffer的内容没有被清除:" + buffer.get(2));//c,get(5)=" "
        System.out.println("执行绝对读取后,position=" + buffer.position());//0
    }

}
