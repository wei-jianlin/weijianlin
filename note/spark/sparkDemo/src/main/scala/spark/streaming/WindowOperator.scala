/*
package spark.streaming

import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.storage.StorageLevel
import scala.collection.immutable.Set
import kafka.serializer.StringDecoder


object WindowOperator {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("SparkStreamingOnKafkaDirect").setMaster("local[*]")

    val ssc = new StreamingContext(sparkConf, Seconds(2))
    ssc.sparkContext.setLogLevel("WARN")
    ssc.checkpoint("file:///d:/data/sparkstreaming/WindowOperator")
    
     val initStream = ssc.socketTextStream("192.168.88.111",9999)
    
  /*  initStream
      .flatMap { _.split(" ") }
      .map { (_,1) }
      .reduceByKeyAndWindow((v1:Int,v2:Int)=>v1+v2, Seconds(10), Seconds(2))
      .print()*/
      
    initStream
      .flatMap { _.split(" ") }
      .map { (_,1) }
      .reduceByKeyAndWindow((v1:Int,v2:Int)=>v1+v2,(v1:Int,v2:Int)=>v1-v2, Seconds(10), Seconds(2))
      .print() 
      
    ssc.start()
    ssc.awaitTermination()
  }
}*/
