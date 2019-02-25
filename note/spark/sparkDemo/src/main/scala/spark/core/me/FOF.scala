package spark.core.me

import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ListBuffer

object FOF {

  def main(args: Array[String]): Unit = {
/*    hadoop tom world hive
      tom hello hadoop cat
      world hello hive hadoop
      hive cat hadoop world mr hello*/

    val sparkSession = SparkSession.builder().appName("FOF").master("local").getOrCreate()
    val sparkContext = sparkSession.sparkContext
    val rdd = sparkContext.textFile("/data/fof.txt")
    //直接好友关系(hadoop,tom)
    val friendRdd = rdd.flatMap(x => {
      val friends = x.split(" ")
      var tuples = new ListBuffer[(Friend,Int)]()
      for(i <- 1 until friends.length){
        val friend = Friend(friends(0),friends(i))
        tuples = tuples:+(friend,1)
      }
      tuples
    }).distinct()

    //间接好友关系(tom,world)
    val rdd2 = rdd.flatMap(x => {
      val friends = x.split(" ")
      var tuples = new ListBuffer[(Friend,Int)]()
      for(i <- 1 until friends.length - 1;j <- i + 1 until friends.length){
        val friend = Friend(friends(i),friends(j))
        tuples = tuples:+(friend,1)
      }
      tuples
    })
    val collect = friendRdd.collect()
    val rdd3 = rdd2.filter(x => {
      var flag = true
      collect.foreach( y => {
        if(x._1.compareTo(y._1) == 0){
          flag = false
        }
      })
      flag
    })
    rdd3.reduceByKey(_+_).sortByKey().sortBy(x => {
      x._2
    },ascending = false).foreach(x => {
      println(x._1.a + ":" + x._1.b + "   count:" + x._2)
    })
  }
}
case class Friend(a : String,b : String) extends Comparable[Friend]{
  override def compareTo(o: Friend): Int = {
    if(this.a.equals(o.a) && this.b.equals(o.b)) return 0
    if(this.a.equals(o.b) && this.b.equals(o.a)) return 0
    1
  }

  override def equals(obj: scala.Any): Boolean = {
/*    if(obj.isInstanceOf[Friend]){
      val friend = obj.asInstanceOf[Friend]
      if(this.a.equals(friend.a) && this.b.equals(friend.b)) return true
      if(this.a.equals(friend.b) && this.b.equals(friend.a)) return true
    }*/
    obj match{
      case friend : Friend =>
        if(this.a.equals(friend.a) && this.b.equals(friend.b)) return true
        if(this.a.equals(friend.b) && this.b.equals(friend.a)) return true
    }
    false
  }

  override def hashCode(): Int = {
    this.a.hashCode + this.b.hashCode
  }
}
