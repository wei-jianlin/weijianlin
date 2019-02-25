package spark.sql.sxt

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.Row

object RDD2DSDynamic {
  def main(args: Array[String]): Unit = {
     val spark = SparkSession
      .builder()
      .appName("RDD2DS")
      .master("local")
      .getOrCreate()
      
    import spark.implicits._  
    
    val peopleRDD = spark.sparkContext.textFile("file:///C:\\702\\尚学堂大数据\\大数据二期\\Spark-14\\spark sql core+data\\spark sql data\\data\\people.txt")
    val schemaString = "name age"
    /*val fields = schemaString.split(" ")
        .map(fieldName => StructField(fieldName, StringType, nullable = true))
    val schema = StructType(fields)    
    
    val rowRDD = peopleRDD
        .map(_.split(","))
        .map(attributes => Row(attributes(0), attributes(1).trim))
    
    val peopleDF = spark.createDataFrame(rowRDD, schema)*/
    
    val RDD = peopleRDD.map { x => {
      val splited = x.split(",")
      (splited(0),splited(1))
    } }
     val peopleDF = RDD.toDF("name","age")
    peopleDF.show()
    peopleDF.createGlobalTempView("people")
    val results = spark.sql("SELECT name FROM global_temp.people")
    results.map(attributes => "Name: " + attributes(0)).show()
    
  }
}