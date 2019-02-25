package spark.core.sxt

import org.apache.spark.sql.SparkSession
import scala.collection.mutable.HashSet
import org.apache.spark.util.AccumulatorV2

/**
 * 教的是一个自定义累加器的套路
 * 需求：
 * 		某几个字段的值进行拼接  使用\t拼接
 * 注意点：
 * 		刚才我们使用累加器进行去重，实际上如果数据量很大，这是有问题
 * 		如果数据规模不大，这种方式比直接使用distinct算子效率要高
 * 		distinct = map + reduceByKey（shuffle 网络IO） + map.....
 */
object UserDefinedAccumulator {
    def main(args: Array[String]): Unit = {
      
    val spark = SparkSession
                   .builder()
                    .appName("PageRank")
                    .master("local")
                    .getOrCreate()
    val sc = spark.sparkContext
    
    
    val rdd = sc.makeRDD(Array(1,1,1,1,2,2,3,4,5,6))
    /**
     * 自定义一个累加器，将RDD中的数据进行去重
     */
    val acc = new UserDAcc()
    sc.register(acc, "distinctAcc")
    
    rdd.foreach { x => {
      acc.add(x)
    } }
    
    acc.value.foreach { println }
    spark.close()
    }
}
class UserDAcc extends AccumulatorV2[Int,HashSet[Int]]{
  
  val set = new HashSet[Int]()
  //添加，往HashSet集合中添加元素  v:传入到累加器中的值
  override def add(v: Int): Unit = {
    set.add(v)
  }
  
  override def copy(): AccumulatorV2[Int,HashSet[Int]] = {
    val acc = new UserDAcc  
    acc
  }
  
  //判断集合是否为空
  override def isZero: Boolean = {
    set.isEmpty
  }
  
  /**
   * 每一个task的累加加过进行合并
   * executor task如果要往累加器中累加元素，实际上就是想Driver发送了一个消息(elem)
   * Driver端记录了每一个task的累加结果，最后的时候将每一个task的累加结果进行合并
   */
  override def merge(other: AccumulatorV2[Int,HashSet[Int]]): Unit = {
    set.++=(other.value)
  }
  
  override def reset(): Unit = {
    set.clear()
  }
  
  //返回最终累加器的结果
  override def value: HashSet[Int] = {
    set
  }
  
}


