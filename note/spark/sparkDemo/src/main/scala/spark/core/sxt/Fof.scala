package spark.core.sxt

import org.apache.spark.sql.SparkSession
import scala.collection.mutable.ListBuffer

object Fof {
  def main(args: Array[String]): Unit = {
    /* hadoop tom world hive
      tom hello hadoop cat
      world hello hive hadoop
      hive cat hadoop world mr hello */
    val spark = SparkSession.builder().appName("Fof").master("local").getOrCreate()
    val sc = spark.sparkContext
    val rdd = sc.textFile("file:///d:/data/friend")
      
      /**
       * 基于rdd进行挖掘，挖掘出间接以及直接的好友关系
       */
     val friendRDD = rdd.flatMap { x => {
       val friends = new ListBuffer[(String,Int)]()
       val persons = x.split("\t")
       val me = persons(0)
      
       for(i <- 1 until persons.length){
          //先来挖掘直接好友关系
         val directFriends = fof(me,persons(i))
         friends.+=((directFriends,0))
         //挖掘间接好友关系
         for(j <- i+1 until persons.length){
           val jjFriend = fof(persons(i),persons(j))
           friends.+=((jjFriend,1))
         }
       }
       friends
     } } 
     
     friendRDD.foreach(println)
     
     //分组，然后查看组内是否有0 ，若有0 这组就废了  否则组内的1累加起来，这就是间接好友出现的次数，以后推荐的时候，最应该推荐出现次数最多的
      val restRDD = friendRDD
        .groupByKey()
        .flatMap(x=>{
          val rest = new ListBuffer[(String,Int)]()
          val friend = x._1
          val items = x._2
          var flag = 0
          var count = 0
          for(elem <- items){
            if(elem == 0){
              flag = 1
            }
            count += 1
          }
          if(flag == 0){
            println(friend + "_" + count)
            rest.+=((friend,count))
          }
          rest
        }).sortBy(x=>x._2,false)
        restRDD.saveAsTextFile("file:///d:/data/frientREST")
  }
  
  def fof(name1:String,name2:String):String = {
    if(name1.compareTo(name2)>0){
      name1 + "_" + name2
    }else{
       name2 + "_" + name1
    }
  }
}