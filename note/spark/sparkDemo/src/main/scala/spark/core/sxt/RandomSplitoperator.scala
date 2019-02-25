package spark.core.sxt

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

/**
 * RandomSplit随机切割rdd
 */
object RandomSplitoperator {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("RandomSplitoperator").master("local").getOrCreate()
    val sc = spark.sparkContext

    val rdd = sc.makeRDD(1 to 10)
 
    
    //randomSplit将RDD切割出来多个  每一个RDD中的数据量受数据中的分值决定的
    //rdd切割成2个rdd    rdd-1 中随机的40%的数据    rdd-2 中随机的60%的数据
    //我们之前说如果返回值是rdd类型，那么这个算子是Transformation类算子
    //如果是非RDD类型，action算子，但是这个标准在遇到randomSplit之后就不好使了
    //randomSplit返回值虽然是一个非RDD类型，但是他是一个transformation类算子
    val splitRDD = rdd.randomSplit(Array(0.4, 0.6))
    println("splitRDD.size:" + splitRDD.size)
    
  }
}