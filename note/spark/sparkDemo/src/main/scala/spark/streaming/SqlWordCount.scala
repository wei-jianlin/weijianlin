package spark.streaming

import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.Time
import org.apache.spark.sql.SparkSession

object SqlWordCount {
  //MEMORY_AND_DISK_SER_2
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("SqlWordCount").setMaster("local[*]")
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    ssc.sparkContext.setLogLevel("WARN")  
    val lines = ssc.socketTextStream("192.168.88.111", 9999,StorageLevel.MEMORY_ONLY)
    val words = lines.flatMap(_.split(" "))
    
    /**
     * output operatror类算子
     * 		将DStream中的RDD抽取出来，然后直接基于RDD进行操作
     */
     words.foreachRDD{ (rdd: RDD[String], time: Time) =>
       
      val spark = SparkSessionSingleton.getInstance(rdd.sparkContext.getConf)
      //方便将一个RDD 转成DS
      import spark.implicits._

      val wordsDataFrame = rdd.map(w => Record(w)).toDF()

      wordsDataFrame.createOrReplaceTempView("words")

      val wordCountsDataFrame =
        spark.sql("select word, count(*) as total from words group by word")
      println(s"========= $time =========")
      wordCountsDataFrame.show()
    }
    ssc.start()
    ssc.awaitTermination()
  }
}
case class Record(word: String)

object SparkSessionSingleton {

  @transient  private var instance: SparkSession = _

  def getInstance(sparkConf: SparkConf): SparkSession = {
    if (instance == null) {
      instance = SparkSession
        .builder
        .config(sparkConf)
        .getOrCreate()
    }
    instance
  }
}