/*
package spark.streaming

import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.storage.StorageLevel
import scala.collection.mutable.HashMap

object SparkStreamingOnKafkaReceiver {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("SparkStreamingOnKafkaReceiver").setMaster("local[*]")
    
    //开启wal机制
    sparkConf.set("spark.streaming.receiver.writeAheadLog.enable", "true")

    val ssc = new StreamingContext(sparkConf, Seconds(3))
    ssc.sparkContext.setLogLevel("WARN")
    //开启WAL机制后，接受来的数据的存放位置
    ssc.checkpoint("file:///d:/data/sparkstreaming002")
    
    
    val zkQuorum = "192.168.88.112:2181,192.168.88.113:2181,192.168.88.114:2181"
    
    //3启动3个receiver task来接受数据
    val topics = Map("sxt" -> 1)
    
    //"abc"表示的是消费者组名
    //inputDStream KV格式的DStream    
    /**
     * kafka中存储的数据是以KV对的形式来存储的，但是我们刚才通过命令行往kafka生产数据的时候没有设置key的值
     * 那么sxt这个topic中k就是nulll
     */
    val inputDStream = KafkaUtils.createStream(ssc, zkQuorum, "abc", topics)
     inputDStream
      .flatMap { _._2.split(" ") }
      .map { (_,1) }
      .reduceByKey(_+_)
      .print()
      
    ssc.start()
    ssc.awaitTermination()
  }
}*/
