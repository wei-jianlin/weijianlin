package spark.core.me

import org.apache.spark.sql.SparkSession

object CacheTest {

  def main(args: Array[String]): Unit = {
    val sparkSession  = SparkSession.builder().appName("cacheTest").master("local").getOrCreate()
    val sparkContext = sparkSession.sparkContext
    val rdd = sparkContext.textFile("file:///C:\\702\\大数据二期\\spark-02\\Spark-StructuredStreaming\\pom.xml")
    rdd.cache()
    var startTime = System.currentTimeMillis()
    var count = rdd.count()
    var endTime = System.currentTimeMillis()
    println("count:" + count + "\tdurations:" + (endTime-startTime) + " ms")

    val startTime1 = System.currentTimeMillis()
    val count1 = rdd.count()
    val endTime1 = System.currentTimeMillis()
    println("count1:" + count1 + "\tdurations:" + (endTime1-startTime1) + " ms")

    sparkSession.close()
  }
}
