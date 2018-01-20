import java.nio.CharBuffer;

public class BufferTest {

    public static void main(String[] args) {
        //����һ������Ϊcapacity �� Xxxbuffer����.
        CharBuffer buffer = CharBuffer.allocate(8);
        System.out.println(buffer.capacity());//�����������,�������ܸı�
        System.out.println(buffer.limit());//λ��limit��������ݼȲ����Ա���,Ҳ�����Ա�д
        //����ָ����һ�����Ա������Ļ���д��Ļ�����λ������,ÿ����һЩ����,buffer��position��Ӧ������ƶ�һЩλ��
        System.out.println(buffer.position());
        buffer.put('a');
        buffer.put('b');
        buffer.put('c');
        System.out.println("��������Ԫ�غ�,position��λ��:" + buffer.position());//3
        //Ϊ���������׼��:��bufferװ�����ݽ�����,����flip����,�÷�����limit����Ϊpositionλ��,��position��Ϊ0,
        //����ʹ�ô�buffer������ʱ�ܴ�0��ʼ,����ո�װ����������ݽ���.
        buffer.flip();
        System.out.println("ִ��flip��,limit��λ��:"+buffer.limit());//3
        System.out.println("ִ��flip��,position��λ��:"+buffer.position());//0
        System.out.println("ȡ����һ��Ԫ��:" + buffer.get());//a
        System.out.println("ȡ��һ��Ԫ�غ�,position=" + buffer.position());//1
        //Ϊװ����������׼��:��buffer������ݽ�����,clear������position��Ϊ0,limit��Ϊcapacity
        buffer.clear();
        System.out.println("ִ��clear��,limit��λ��:"+buffer.limit());//8
        System.out.println("ִ��clear��,position��λ��:"+buffer.position());//0
        System.out.println("ִ��clear��,buffer������û�б����:" + buffer.get(2));//c,get(5)=" "
        System.out.println("ִ�о��Զ�ȡ��,position=" + buffer.position());//0
    }

}
