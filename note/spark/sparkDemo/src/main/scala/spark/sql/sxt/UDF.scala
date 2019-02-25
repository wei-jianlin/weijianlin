package spark.sql.sxt

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.DataTypes

object UDF {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("UDF")
      .master("local")
      .getOrCreate()
      
    import spark.implicits._ 
      
    val list = List(1,2,3,4)
    val df = list.toDF("id")
    
    spark.udf.register("addOne", (elem:Int) => elem+1)
    df.createTempView("tbl")
    
    spark.sql("select addOne(id) as plusId from tbl").show()
    df.show()
    
  }
}
