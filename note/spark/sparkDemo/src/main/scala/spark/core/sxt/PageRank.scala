package spark.core.sxt

import org.apache.spark.sql.SparkSession

/**
 *	A	B	D
    B	C
    C	A	B
    D	B	C
 */
object PageRank {
  def main(args: Array[String]): Unit = {
      val spark = SparkSession
                   .builder()
                    .appName("PageRank")
                    .master("local")
                    .getOrCreate()
    val sc = spark.sparkContext
    val rdd = sc.textFile("file:///d:/data/pagerank.txt")
    
    /**
     * 计算页面总数
     * distinct = map.reduceByKey.map
     */
    val pageCount = rdd.flatMap { x => x.split("\t") }.distinct().count()
    
    /**
     * 广播变量的注意点：
     * 		1、广播变量只能在Driver端定义 因为创建一个广播变量需要使用sc对象，sc对应只是存在于Driver进程中
     * 		2、广播变量在Executor端只能读，不能修改
     * 		3、若想修改广播变量的值，只能在Driver端重新创建一个新的广播变量
     */
    var brpc = sc.broadcast(pageCount)
    val brzn = sc.broadcast(0.85)
    
    /**
     * 累加器：集群规模间的一个大变量
     * 累加器是维护在Driver中
     * 每一个Executor可以操作累积器中的中，这里面线程安全，以及变量共享问题 Driver端已经解决
     * 注意点：
     * 		1、在Driver定义
     * 		2、累积器中值只能在Driver端读取
     */
    //创建一个double类型的累加器   这个累加器只能累加double类型的值
    val acc = sc.doubleAccumulator("error")
    
    var nodeRDD = rdd.map { line => {
      val nodes = line.split("\t")
      val me = nodes(0)
      val node = Node(1.0,nodes.drop(1))
      (me,node)      
    } }
      
//    var error = 0.0
    var limit = 0.01
    var flag = true
    while(flag){
        /**
         * 使用flatMap来对相邻的节点进行投票
         * 		1、计算票面值
         * 		2、投票
         */
        val rankRDD = nodeRDD.flatMap(n => {
          val currentNode = n._1
          val node = n._2
          val pr = node.pr
          val adjacentNodes = node.adjacentNodes
          val pmz = pr/adjacentNodes.length
          adjacentNodes.map { x => (x,pmz) }       
        }).reduceByKey(_+_)
        
         // RDD[(String, (Node, W))]
       nodeRDD = nodeRDD.join(rankRDD)
        .map(line => {
               val node = line._2._1   
               val newPr = ((1-brzn.value)/brpc.value) + (brzn.value*line._2._2)
               val key = line._1
               acc.add(Math.abs(newPr-node.pr))
               node.pr = newPr
               println(acc.value)
               (key,node)
        })
        nodeRDD.count()
        println("error:" + acc.value)
        if(acc.value < limit){
          flag = false
        }
        //累积器的值归0
        acc.reset()
      }
      nodeRDD.foreach(x=>{
          val page = x._1
          val pr = x._2.pr
          println("page:" + page + "=" + pr)
        })
  }
}

case class Node(var pr:Double , var adjacentNodes:Array[String])



