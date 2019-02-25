package spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming._
import org.apache.spark.storage.StorageLevel
/*
 * 1、local的模拟线程数必须大于等于2 因为一条线程被receiver(接受数据的线程)占用，另外一个线程是job执行
 * 2、Durations时间的设置，就是我们能接受的延迟度，这个我们需要根据集群的资源情况以及监控，ganglia  每一个job的执行时间
 * 3、 创建StreamingContext有两种方式 （sparkconf、sparkcontext）
 * 4、业务逻辑完成后，需要有一个output operator类算子，类似core中的action类算子
 * 5、JavaStreamingContext.start() straming框架启动之后是不能在次添加业务逻辑
 * 6、JavaStreamingContext.stop()无参的stop方法会将sparkContext一同关闭，stop(false)
 * 7、JavaStreamingContext.stop() 停止之后是不能在调用start   
 */
object SparkStreamingOnSocket {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
                  .setAppName("WordCountOnline")
                  /**
                   * master 
                   * 		local:使用1个线程来模拟执行
                   * 		local[2]:使用2个线程来模拟执行
                   * 		local[*]:你的机器上还有多少个剩余的core，那么就会启动多少个线程来模拟执行
                   */
                   
                  .setMaster("local[2]")
                  //minuts   Milliseconds
    val ssc = new StreamingContext(conf,Seconds(5))
    
    //socketTextStream 处理socket中的文本数据
    /**
     * 每个5s中的数据与initStream形成映射
     *  nc -lk 9999
     */
    val initStream = ssc.socketTextStream("node01",9999)
    
    initStream
      .flatMap { _.split(" ") }
      .map { (_,1) }
      .reduceByKey(_+_)
      //在SparkStreaming中必须有一个output operator类的算子 
      .print()
       
    ssc.start()
    //awaitTermination 等待关闭  若不调用这个方法，代码只会被执行一次
    ssc.awaitTermination()
  }
}