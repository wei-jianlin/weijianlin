package spark.sql.sxt

import org.apache.spark.sql.SparkSession
import scala.util.Properties
import java.util.Properties
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import org.apache.spark.sql.SaveMode

/**
 * 读取mysql中的数据，有三套api可以选择
 * 	选择自己喜欢就好，没有什么效率区别
 */
object ReadMySQL {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("ReadMySQL")
      .master("local")
      .getOrCreate()
      
      
   /* 第一种连接mysql的方式
    * val jdbcDF = spark.read
                  .format("jdbc")
                  .option("url", "jdbc:mysql://192.168.88.115:3306/test")
                  .option("dbtable", "userInfo")
                  .option("user", "root")
                  .option("password", "123")
                  .load()*/
    /**
     * 第二种链接mysql的方式 
     */
    val properties =  new Properties()
    properties.put("user", "root")
    properties.put("password", "123")
    val userInfoDF = spark.read.jdbc("jdbc:mysql://192.168.88.115:3306/test", "userInfo", properties)
    
    /**
     * 第三种链接方式：    
     */
    var options = new HashMap[String, String]();
    options.put("url", "jdbc:mysql://192.168.88.115:3306/test");
    options.put("user", "root");
    options.put("password", "123");
    options.put("dbtable", "facePower");
    var facePowerDF = spark.read.format("jdbc").options(options).load()
    
    
    
    val restDF = userInfoDF
                  .join(facePowerDF, userInfoDF.col("id").===(facePowerDF.col("id")))
//                   .select("id", "name","height")
                  .select(userInfoDF.col("id"),userInfoDF.col("name"),userInfoDF.col("age"),userInfoDF.col("height"),facePowerDF.col("facePower"))
                  
    restDF
      .write
//      Overwrite重写    先将表的数据进行truncate ， 在去写数据  你要保证有这个表
      .mode(SaveMode.Overwrite)
      .option("createTableColumnTypes", "id TINYINT, name VARCHAR(64), age SMALLINT,height DOUBLE,facePower DOUBLE")
      /**
       * rest表如果不存在，那么他会自动创建
       * 他是根据restDF中每一个列的类型来推测
       */
      .jdbc("jdbc:mysql://192.168.88.115:3306/test", "rest", properties)
    
         
      
    /**
     * 提高读写数据库数据的效率
     * 
     */
       val testDF = spark
                     .read
                     .option("partitionColumn", "id")
                     .option("lowerBound", "1")
                     .option("upperBound", "16")
                     .option("numPartitions", "3")
                     .option("fetchsize", "5") //fetchsize每次分批读取数据，每批次读取5条数据
                     .jdbc("jdbc:mysql://192.168.88.115:3306/test", "test", properties)
                     
       println(testDF.rdd.getNumPartitions)
       
       testDF.rdd.mapPartitionsWithIndex((index,iterator)=>{
         println("index:" + index)
         while(iterator.hasNext){
           println(iterator.next())
         }
         println("=====================")
         iterator
       }, true).count()
  }
}