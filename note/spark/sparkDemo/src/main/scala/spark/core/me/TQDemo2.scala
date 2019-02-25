package spark.core.me

import java.util

import org.apache.spark.sql.SparkSession

object TQDemo2 {

  def main(args: Array[String]): Unit = {
    //统计每月气温最高的两天
    val sparkSession = SparkSession.builder().appName("TQ").master("local").getOrCreate()
    val sparkContext = sparkSession.sparkContext

    //file://代表读的是本地  如果不加并且你还将hdfs-site.xml以及core-site.xml放入在工程，那么他会认为这是一个HDFS路径
    val rdd = sparkContext.textFile("file:///C:\\weijianlin\\workspaces\\hadoop\\hadoopMRTest\\src\\com\\weijianlin\\hadoop\\weather\\weather")
    val rdd2 = rdd.map(x => {
      val year = x.substring(0,4).toInt
      val month = x.substring(5,7).toInt
      val wendu = x.substring(x.indexOf("\t") + 1,x.length - 1).toInt
      (TQ(year,month,wendu),x)
    })
    rdd2.sortByKey(ascending = false)
      .map(x => {
        val tq = x._1
        (tq.year + "_" + tq.month,x._2)
      }).groupByKey().map(x => {
        var day = ""
        var count = 0
        val list = new util.ArrayList[(String,Int)]()
        x._2.foreach(y => {
          if(!day.equals(y.substring(0,10)) && count < 2){
            day = y.substring(0,10)
            count = count + 1
            val wendu = y.substring(y.indexOf("\t") + 1,y.length - 1).toInt
            list.add((day,wendu))
          }
        })
      (x._1,list)
    }).saveAsTextFile("file:///c:/tqoutput1")
    sparkSession.close()
  }
}
case class TQ(year : Int,month : Int,wendu : Int) extends Ordered[TQ] with Serializable {
  override def compare(that: TQ): Int = {
    if(this.year != that.year){
      return this.year - that.year
    }else if(this.month != that.month){
      return this.month - that.month
    }else if(this.wendu != that.wendu){
      return this.wendu - that.wendu
    }
    0
  }
}
