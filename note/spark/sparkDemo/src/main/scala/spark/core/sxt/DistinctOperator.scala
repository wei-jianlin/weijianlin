package spark.core.sxt

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object DistinctOperator {
  def main(args: Array[String]): Unit = {
     val spark = SparkSession.builder().appName("DistinctOperator").master("local").getOrCreate()
    val sc = spark.sparkContext
    val rdd = sc.makeRDD(Array(1,2,1,11,6,6,3,2))
    
    /**
     * map  KV格式的RDD
     * (1,null)
     * (2,null)
     * (2,null)
     * 
     * 
     * groupByKey/reduceByKey算子，可以对key去重
     * 1 [null]
     * 2 [null,null]
     * 
     */
    rdd.map { (_,null) }.groupByKey().map(_._1).foreach { println }
       
     
     
  }
}