package spark.sql.sxt

import org.apache.spark.sql.SparkSession

object ReadParquetFile {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("ReadParquetFile")
      .master("local")
      .getOrCreate()
      
    val usersDF = spark.read.load("file:///C:\\702\\尚学堂大数据\\大数据二期\\Spark-14\\spark sql core+data\\spark sql data\\data/users.parquet")
    
    usersDF.show()
    usersDF.printSchema()
    
//    usersDF.select("name", "favorite_color").write.save("file:///d:/data/namesAndFavColors.parquet")
     val namesAndFavColorsDF = spark.read.load("file:///C:\\702\\尚学堂大数据\\大数据二期\\Spark-14\\spark sql core+data\\spark sql data\\data/namesAndFavColors.parquet")
    namesAndFavColorsDF.show()
    
    /**
     * 直接对parquet格式的文件进行SQL处理
     */
   spark.sql("select name from parquet.`file:///C:\\702\\尚学堂大数据\\大数据二期\\Spark-14\\spark sql core+data\\spark sql data\\data/namesAndFavColors.parquet`").show()
    
    
     /**
       *自动推测分区信息
       *对于json文件是无法推测的
      */ 
    val discoverPartitionDF = spark.read.load("file:///C:\\702\\尚学堂大数据\\大数据二期\\Spark-14\\spark sql core+data\\spark sql data\\data/parquetdata")
    discoverPartitionDF.printSchema()
    discoverPartitionDF.show
    
   /**
     * schema merge 合并模式
     * 说白了就是讲两种数据类型、列不一致的DS合并到一张表中
     */
    val df1 = spark.read.load("file:///C:\\702\\尚学堂大数据\\大数据二期\\Spark-14\\spark sql core+data\\spark sql data\\data/namesAndFavColors.parquet").write.save("file:///d:/data/rest/info=1")
    val df2 = spark.read.load("file:///d:/data/users.parquet").write.save("file:///d:/data/rest/info=2")
    val unionDF = spark.read.option("mergeSchema", "true").parquet("file:///d:/data/rest")
    unionDF.show()
    unionDF.printSchema() 
    
    
  }
}