package spark.core

import org.apache.spark.sql.SparkSession

object PangRank2 {

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("pageRank").master("local").getOrCreate()
    val sparkContext = sparkSession.sparkContext
    val rdd = sparkContext.textFile("file:///C:\\702\\大数据二期\\Spark-11\\pangRank.txt")

  }
}
case class Node()
