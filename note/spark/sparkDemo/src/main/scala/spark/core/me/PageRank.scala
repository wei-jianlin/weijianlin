package spark.core.me

import org.apache.spark.sql.SparkSession

object PageRank {

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("pageRank").master("local").getOrCreate()
    val sparkContext = sparkSession.sparkContext
    val rdd = sparkContext.textFile("file:///C:\\702\\大数据二期\\Spark-11\\pangRank.txt")
    //(A,1)
    var keyPR = rdd.map(x => {
      val arr = x.split("\t")
      (arr(0),1D)
    }).distinct()
    val count = keyPR.count()
    keyPR = keyPR.cache()
    /**
      * 广播变量的注意点：
      *     0.运行在Executor 的所有task都共享一份广播变量,比在task中定义一个变量更省内存
      * 		1、广播变量只能在Driver端定义 因为创建一个广播变量需要使用sc对象，sc对应只是存在于Driver进程中
      * 		2、广播变量在Executor端只能读，不能修改
      * 		3、若想修改广播变量的值，只能在Driver端重新创建一个新的广播变量
      */
    val brzn = sparkContext.broadcast(0.85)
    val brcount = sparkContext.broadcast(count)
    /**
      * 累加器：集群规模间的一个大变量
      * 累加器是维护在Driver中
      * 每一个Executor可以操作累积器中的中，这里面线程安全，以及变量共享问题 Driver端已经解决
      * 注意点：
      * 		1、在Driver定义
      * 		2、累积器中值只能在Driver端读取
      */
    //创建一个double类型的累加器   这个累加器只能累加double类型的值
    val acc = sparkContext.doubleAccumulator("error")
    //(A,[B,C])
    val nodes = rdd.map(x => {
      val arr = x.split("\t")
      (arr(0),arr.drop(1))
    })
    var i = 0
    val limit = 0.01
    var flag = true
    while(i < 1){
      i = i + 1
      //(A,(1,[B,C]))
      //(A,1/2)
      keyPR = keyPR.join(nodes,4).flatMap(y => {
        val oldPR : Double = y._2._1
        val nodeArr = y._2._2
        val newPR = (1 - brzn.value)/brcount.value + brzn.value * oldPR/nodeArr.length
        //计算与上一次的PR值得差,这个是错的
        acc.add(Math.abs(oldPR - newPR))
        nodeArr.map(z => {
          (z,newPR)
        })
      }).reduceByKey(_+_,4)
      if(acc.value < limit){
        flag = false
      }
      //累积器的值归0
      acc.reset()
    }
    keyPR.foreach(x => {
      println(x)
    })
    sparkSession.close()
  }
}
