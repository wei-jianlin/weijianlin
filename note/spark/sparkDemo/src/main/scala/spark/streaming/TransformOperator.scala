package spark.streaming

import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.SparkConf


/**
 * 通过ID查询出对应的地址
 * foreachRDD
 */
object TransformOperator {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("TransformOperator").setMaster("local[*]")
    val ssc = new StreamingContext(sparkConf, Seconds(3))
    
    /*
     * KV格式的RDD，映射的是一个字典表
     */
    val id2AreaRDD = ssc.sparkContext.makeRDD(
        Array(
            (1,"beijing"),
            (2,"shanghai"),
            (3,"guangzhou"),
            (4,"shenzhen")
            )
        )    

    /**
     * 如果有一个字典表的数据映射到RDD中，那么使用DStream是无法与RDD进行join的
     * 这个时候可以考虑使用transform将DStream的RDD抽取出来，然后进行join
     * transform的另外一个好处就是对于不熟悉DStream Api是一个很大的福音
     */
    val lines = ssc.socketTextStream("192.168.88.111", 9999)
    
    //transform方法返回值必须是RDD
 /*   val areaDStream = lines.transform(rdd=>{
      //rdd -> k:原值  v:null
      val id2NullRDD = rdd.map { x=>(x.trim().toInt,null) }
      val areaNameRDD = id2NullRDD.join(id2AreaRDD).map(x=>x._2._2)
      areaNameRDD
    })*/
    
//    areaDStream.print()
    
    lines.foreachRDD { rdd => {
      rdd
        .map(x=>(x.trim().toInt,null))
        .join(id2AreaRDD)
        .foreach(x=>{
          println("=========================")
          println(x._2._2)
          println("=========================")
        })
    } }
    ssc.start()
    ssc.awaitTermination()
  }
}