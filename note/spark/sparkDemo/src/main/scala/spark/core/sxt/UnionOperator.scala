package spark.core.sxt

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import scala.collection.mutable.ListBuffer
import org.apache.spark.sql.SparkSession

object UnionOperator {
  def main(args: Array[String]): Unit = {
   val spark = SparkSession.builder().appName("UnionOperator").master("local").getOrCreate()
    val sc = spark.sparkContext
    
    val rdd1 = sc.parallelize(1 to 10,2)   // 1 2 3 4 5 10
    
    val rdd2 = sc.makeRDD(2 to 11,2)// 1 2 3 4 ..9 
    
    val rdd3 = sc.makeRDD(Array("A","B","C"))
    
    /**
     * 实际上在底层 并不会将RDD1和RDD2真正合并，并没有数据的传输
     * 而是逻辑上将RDD1和RDD2看成是一个组合，组合名叫unionRDD
     * 
     * union列向合并
     * join横向关联
     * 
     */
    val unionRDD = rdd1.union(rdd2)
    println(unionRDD.count())
    unionRDD.foreach { println }
   
    /**
     * 差集
     */
    rdd1.subtract(rdd2).foreach { println }
    /**
     * 交集
     */
    rdd1.intersection(rdd2).foreach { println }
  }
}