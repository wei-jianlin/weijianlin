package spark.core.me

import org.apache.spark.sql.SparkSession

object WorldCount {

  def main(args: Array[String]): Unit = {
      val sparkSession = SparkSession.builder().appName("worldCount").master("local").getOrCreate()
      val sparkContext = sparkSession.sparkContext
      //RDD of lines of the text file : RDD[String]
      val rdd = sparkContext.textFile("file:///C:\\702\\大数据二期\\spark-02\\Spark-StructuredStreaming\\pom.xml",2)
      //flatMap(f : U => Seq[U])
      val worldRdd = rdd.flatMap(x => {
        x.split(" ")
      })
      // map(f : T => U)
      val pairRDD = worldRdd.map(x => {
        (x,1)
      })
      //reduceByKey(f : (T,T) = > T)
      pairRDD.reduceByKey((v1,v2) => {
        v1 + v2
      }).foreach(x => {
        println(x._1 + ":" + x._2)
      })
      sparkSession.close()
  }
}
