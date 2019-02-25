package spark.streaming

import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.SparkConf
import org.apache.spark.HashPartitioner

/**
 * reduceByKey 这些算子都是无状态的
 * UpdateStateByKey
 * 	为每一个Key维护一份state状态，
 * 	state类型可以是任意类型的,状态也可以是一个自定义的类型
 *  更新函数也可以是自定义的
 *  
 *  通过更新函数对该key的状态不断更新，
 *  对于每个新的batch(5s)-->RDD-->DStream
 *  Spark Streaming会在使用updateStateByKey的时候为已经存在的key进行state的状态更新
 *  
 *  UpdateStateByKey应用场景：
        1、统计广告的点击量、
        2、商品、页面的点击量等
   场景：每隔5s钟查看最近10min的最新结果
   	今日头条
 */
object UpdateStateByKeyOperator {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("UpdateStateByKeyOperator").setMaster("local[*]")
    //batch interval  duration   Seconds(5)
    val ssc = new StreamingContext(sparkConf, Seconds(5))
    
    /**
     * checkpoint directory检查点目录
     * 每隔一段是按  将每一个key所对应的状态进行持久化到磁盘空间  这样的话每一个key的状态就比较稳定了
     * 
     * 
     * 状态维护在内存中，但是内存不稳定，需要将内存中的数据定期输出到磁盘上
		 * 多久会将内存中的数据（每一个key所对应的状态）写入到磁盘上一份呢？
		 * 默认是每隔10s
		 * 	如果你的batch interval小于10s  那么10s会将内存中的数据（每一个key对应的状态）写入到磁盘一份
		 * 	如果bacth interval 大于10s，那么就以bacth interval为准
		 * 
		 */
    ssc.checkpoint("file:///d:/data/checkpoint");
    
    
    val rdd = ssc.sparkContext.makeRDD(
        Array(("hello",1),("bjsxt",1))
        ,2)
    
    val initStream = ssc.socketTextStream("192.168.88.111", 9999)
    val restDStream = initStream
      .flatMap { _.split(" ") }
      .map { (_,1) }
      /**
       * newValues:根据key分组后的 value集合   hello [1,1,1,1]
       * beforeValue:上一次状态更新后的结果
       * updateStateByKey：永远的记录下每一个word出现的次数（status）
       * 1-5s
       * 		hello bjsxt
       * 		hello shsxt
       * 		hello gzsxt
       * 		hello 1
       * 		bjsxt 1
       * 		hello 1
       * 		shsxt 1
       * 		hello 1
       * 		gzsxt 1
       * 		hello [1,1,1]
       * 		bjsxt [1]
       * 		....
       * 		hello 3
       * 
       * 6-10
       * 		hello bjsxt
       * 		hello 1
       * 		bjsxt 1
       * 		hello [1]
       * 		bjsxt [1]
       * 		hello 4
       */
//      .updateStateByKey((newValues:Seq[Int],beforeValue:Option[Int])=>{
//        var initValue = beforeValue.getOrElse(0)
//        for(num <- newValues){
//            initValue += num       
//        }
//        Option(initValue)
//      })
      
      /**
       * rdd 
       * 		k：未来处理 数据的时候 可能出现的key
       * 		v：key对应的status的初始值
       */
      .updateStateByKey((newValues:Seq[Int],beforeValue:Option[Int])=>{
        var initValue = beforeValue.getOrElse(0)
        for(num <- newValues){
            initValue += num       
        }
        Option(initValue)
      }, new HashPartitioner(2), rdd)
      
//      restDStream.transform(x=>{
//        println(x.getNumPartitions)
//        x
//      }).count()
      restDStream.print()
      ssc.start()
      ssc.awaitTermination()
    
  }
}