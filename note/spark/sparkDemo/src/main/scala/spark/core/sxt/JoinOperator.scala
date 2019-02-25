package spark.core.sxt

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object JoinOperator {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("JoinOperator").master("local").getOrCreate()
    val sc = spark.sparkContext
    
    /**
     * 创建RDD的方式：
     * 	1、使用本地集合创建一个RDD   并行化
     * 	2、通过读本地或者HDFS上的文件
     */
    val rdd1 = sc.parallelize(Array(Tuple2("A",1),("B",2),("C",1),Tuple2("A",2)) , 2)
    val rdd2 = sc.makeRDD(Array(Tuple2("A",100),("B",99),("C",101),("D",88)), 3)
    
    
    /**
     * join 内连接
     */
    rdd1.join(rdd2).foreach(println(_))
    
    /**
     * leftOuterJoin 左外连接
     */
    rdd1.leftOuterJoin(rdd2).foreach(println)
    /**
     * rightOuterJoin右外连接
     */
    rdd1.rightOuterJoin(rdd2).foreach(println)
    
    /**
     * fullOuterJoin全连接
     */
    rdd1.fullOuterJoin(rdd2).foreach(println)
  }
}