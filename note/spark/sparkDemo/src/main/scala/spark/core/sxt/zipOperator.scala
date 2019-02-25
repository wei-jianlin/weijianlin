package spark.core.sxt

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import scala.collection.mutable.ListBuffer
import org.apache.spark.sql.SparkSession

object zipOperator {
  def main(args: Array[String]): Unit = {
     val spark = SparkSession.builder().appName("zipOperator").master("local").getOrCreate()
    val sc = spark.sparkContext
    
    val rdd1 = sc.parallelize(1 to 10,2)   // 1 2 3 4 5 10
    
    val rdd2 = sc.makeRDD(2 to 11,3)// 1 2 3 4 ..9
    
    /**
     * zip这个算子的返回值是一个kv格式的RDD
     * 1、分区数需要相同
     * 2、分区中的元素个数相等
     */
//     val rdd3 = rdd1.zip(rdd2)
    /**
     * （1,2）
     * （2，3）
     * ....
     * (10,11)
     */
//     rdd3.foreach(println)
    
    rdd2.mapPartitionsWithIndex((index,values)=>{
      println(index)
      for(valiue <- values){
        println(valiue)
      }
      values
    }, false).count()
    
    
    //zipWithIndex 将rdd中每一个元素打上一个唯一的ID号
    val zipWithIndexRdd = rdd2.zipWithIndex()
    zipWithIndexRdd.foreach(println)

    rdd2.mapPartitionsWithIndex((index,iterator)=>{
       val list = new ListBuffer[Int]()
        while (iterator.hasNext) {
          val v = iterator.next()
          println("partition Id:"+index +"\tvalue："+v)
          list.+=(v)
        }
       list.iterator
    }, false).count()
    
    
//    zipWithUniqueId 步长=分区数
    val zipWithUnipeIdRDD = rdd2.zipWithUniqueId()
    zipWithUnipeIdRDD.foreach(println)
    while(true){}
  }
}