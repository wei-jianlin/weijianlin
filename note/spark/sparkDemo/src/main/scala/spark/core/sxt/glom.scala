package spark.core.sxt

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object glom {
  def main(args: Array[String]): Unit = {
      val spark = SparkSession.builder().appName("PageRank").master("local").getOrCreate()
    val sc = spark.sparkContext
    
    val rdd1 = sc.parallelize(1 to 10,2)   // 1 2 3 4 5 10
    
    /**
     * rdd1有两个分区
     * 	partition0分区里面的所有元素封装到一个数组
     * 	partition2分区里面的所有元素封装到一个数组
     */
    val glomRDD = rdd1.glom()
    glomRDD.foreach { _.foreach { println } }
    println(glomRDD.count())
    
  }
}