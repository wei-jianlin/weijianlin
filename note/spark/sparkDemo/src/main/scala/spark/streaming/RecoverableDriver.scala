package spark.streaming

import org.apache.spark.streaming.StreamingContext

import java.io.File
import java.nio.charset.Charset
import com.google.common.io.Files
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext, Time}
import org.apache.spark.util.{IntParam, LongAccumulator}


object RecoverableDriver {
  def main(args: Array[String]): Unit = {
    val checkpointPath = "file:///c:/data/RecoverableDriver1"
    //val checkpointPath = args(0)
    val ip = "node01"
    val port = 9999
    val ssc = StreamingContext.getOrCreate(checkpointPath, ()=>{
      createContext(ip, port, checkpointPath)
    })
    ssc.start()
    ssc.awaitTermination()
  }
  
  def createContext(ip: String, port: Int, checkpointDirectory: String)
    : StreamingContext = {
    println("init StreamingContext")
    val sparkConf = new SparkConf().setAppName("RecoverableDriver").setMaster("local[2]")
    val ssc = new StreamingContext(sparkConf, Seconds(10))
    ssc.checkpoint(checkpointDirectory)

    val lines = ssc.socketTextStream(ip, port)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map((_, 1)).reduceByKey(_ + _)
    wordCounts.foreachRDD { rdd =>
      val blackNamelist = BlackNamelist.getInstance(rdd.sparkContext)
      val blackNameCounter = BlackNameCounter.getInstance(rdd.sparkContext)
      val counts = rdd.filter { case (word, count) =>
        if (blackNamelist.value.contains(word)) {
          blackNameCounter.add(count)
          false
        } else {
          true
        }
      }.collect().mkString("[", ", ", "]")
      val output = counts
      println(output)
      println("droppedWordsCounter.value " + blackNameCounter.value)
    }
    ssc
  }
}

object BlackNamelist {

  @volatile private var instance: Broadcast[Seq[String]] = null

  def getInstance(sc: SparkContext): Broadcast[Seq[String]] = {
    if (instance == null) {
      synchronized {
        if (instance == null) {
          val blackNamelist = Seq("Angelababy", "Dilireba", "Gulinazha")
          instance = sc.broadcast(blackNamelist)
        }
      }
    }
    instance
  }
}

object BlackNameCounter {

  @volatile private var instance: LongAccumulator = null

  def getInstance(sc: SparkContext): LongAccumulator = {
    if (instance == null) {
      synchronized {
        if (instance == null) {
          instance = sc.longAccumulator("BlackNameCounter")
        }
      }
    }
    instance
  }
}