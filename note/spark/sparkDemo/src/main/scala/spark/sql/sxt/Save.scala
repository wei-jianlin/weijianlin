package spark.sql.sxt

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode

object Save {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("ReadParquetFile")
      .master("local")
      .config("spark.sql.warehouse.dir","file:///d:/data/spark-warehouse")
      .getOrCreate()
      
    
    val usersDF = spark.read.load("file:///d:/data/users.parquet")
    
    usersDF.show()
    usersDF.printSchema()
    
    usersDF.select("name", "favorite_color").write.mode(SaveMode.Overwrite).save("file:///d:/data/namesAndFavColors.parquet")
    val namesAndFavColorsDF = spark.read.load("file:///d:/data/namesAndFavColors.parquet")
    namesAndFavColorsDF.show()
    
    
    /**
     * 默认保存在本工程的spark-warehouse目录下，当然可以通过path方法来指定存放位置
     * 	但是如果是自己指定存储位置，当删除这张表的时候，保存的数据不会删除
     * 	如果使用的是默认路径，那么删除表的时候一同会被删除
     * 
     * 
     * saveAsTable
     * 为什么要以table的形式来存储数据呢？
     * 方便后期去读数据
     */
    namesAndFavColorsDF.write.option("path", "file:///d:/data/tmp111").saveAsTable("tmp")
    //存储没有指定路径的  若没有指定，他会使用默认值，默认值就是在SparkSession中设置的路径  file:///d:/data/spark-warehouse
//    namesAndFavColorsDF.write.saveAsTable("tmp")
    spark.table("tmp").show()
    //show() 类似sparkcore中的action算子，触发执行
    spark.sql("drop table tmp").show()

     /**
     * 分区 分桶来存储
     * hive 分区table  将table的数据分成多个子目录来存储
     */
    val df = spark.read.text("file:///d:/data/Student")
    import spark.implicits._
    val ds = df.map(row=>{
      val str = row(0).toString()
      val splited = str.split("\t")      
      Student(splited(0),splited(1).toInt,splited(2).toDouble,splited(3).split(","))
    })
    
    /**
     * 对表进行分桶存储
     * 
     * 将大表的数据分成多个小文件来存
     */
    ds.write.bucketBy(2, "age").format("json").saveAsTable("people_bucketed")
     
    /**
     * 对表进行分区存储
     * 存储的数据可以供hive 分区表直接来使用
     * 		ALTER TABLE PARTITION ... SET LOCATION  
     */
    ds.write.partitionBy("score").format("json").saveAsTable("people_partition")
//    
//    /**
//     * 同时分区和分桶
//     * 每个分区对应的目录下，会进行分桶
//     */
     ds.write.partitionBy("age").bucketBy(2, "score").format("json").saveAsTable("people_partition_bucket")
//    
  }
}

case class Student(var name:String,var age:Int,var score:Double,var favorite:Array[String])


