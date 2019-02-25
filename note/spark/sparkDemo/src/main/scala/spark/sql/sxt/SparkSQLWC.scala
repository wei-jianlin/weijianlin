package spark.sql.sxt

import org.apache.spark.sql.SparkSession


object SparkSQLWC {
  def main(args: Array[String]): Unit = {
     val spark = SparkSession
      .builder()
      .appName("SparkSQLWC")
      .master("local")
      .getOrCreate()
      
    import spark.implicits._  
    
    /**
     * 直接得到DS
     */
    val initRDD = spark.sparkContext.textFile("file:///d:/wc.txt")
    val initDF = initRDD.toDF("log")
    initDF.createTempView("wc")
    
    initDF.show()
    val wcSQL = "select word,count(0) as wccount "+
                    "from "+
                        " (select explode(split(log,' ')) as word "+
                            "from wc rest) "+
                     "group by word "+
                     "order by wccount desc ";
    spark.sql(wcSQL).show()    
  }
}