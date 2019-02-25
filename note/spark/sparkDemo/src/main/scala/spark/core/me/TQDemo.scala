package spark.core.me

import java.util

import org.apache.spark.sql.SparkSession


object TQDemo {

  def main(args: Array[String]): Unit = {
    //统计每月气温最高的两天
    val sparkSession = SparkSession.builder().appName("TQ").master("local").getOrCreate()
    val sparkContext = sparkSession.sparkContext

    //file://代表读的是本地  如果不加并且你还将hdfs-site.xml以及core-site.xml放入在工程，那么他会认为这是一个HDFS路径
    val rdd = sparkContext.textFile("file:///C:\\weijianlin\\workspaces\\hadoop\\hadoopMRTest\\src\\com\\weijianlin\\hadoop\\weather\\weather")
    val rdd2 = rdd.map(x => {
      val yeadAndMonth = x.substring(0,7)
      val day = x.substring(0,10)
      val wendu = x.substring(x.indexOf("\t") + 1,x.length - 1).toInt
      (yeadAndMonth,(day,wendu))
    })

    val rdd3 = rdd2.groupByKey()
    //同一个stage中,是pipeline计算模式
    val rdd4 = rdd3.map(x => {

      val ite = x._2.toArray.sortBy(_._2)(Ordering[Int].reverse)
      val arr = new util.ArrayList[(String,Int)]()
      var day = ""
      var count = 0

      ite.foreach(z => {
        if(!day.equals(z._1) && count < 2){
          day = z._1
          count = count + 1
          arr.add(z)
        }
      })
      (x._1,arr)
    })
    rdd4.foreach(x => {
      println(x)
    })
    sparkSession.close()
  }
}
