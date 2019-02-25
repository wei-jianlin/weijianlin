package spark.sql.me

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}

object RDDToDataSetDynamic {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("RDD2DS")
      .master("local")
      .getOrCreate()
    val peopleRDD = spark.sparkContext.textFile("file:///C:\\702\\尚学堂大数据\\大数据二期\\Spark-14\\spark sql core+data\\spark sql data\\data\\people.txt")
    val schemaString = "name age"
    val fields = schemaString.split(" ").map(StructField(_, StringType, nullable = true))
    val schema = StructType(fields)
    val rowRDD = peopleRDD.map(x => x.split(","))
    .map(attributes => Row(attributes(0), attributes(1).trim))
    spark.createDataFrame(rowRDD,schema).show()
    println("=======toDf===========")
    val tupleRdd = peopleRDD.map(x => {
      val strs = x.split(",")
      (strs(0),strs(1))
    })
    import spark.implicits._
    val peopleDF = tupleRdd.toDF("name","age")
    peopleDF.createGlobalTempView("people")
    val results = spark.sql("select * from global_temp.people")
    results.map(attributes => "Name: " + attributes(0)).show()
  }
}
