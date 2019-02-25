package spark.sql.sxt

import org.apache.spark.sql.SparkSession

object ReadJsonFile {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("CreateDS")
      .master("local")
      .getOrCreate()

    /**
      * 使用DataSet来做数据处理，那么在写磁盘的时候，会使用spark自带的encoder进行编码，编码成字节码
      */
    import spark.implicits._
    //    val df = spark.read.json("file:///d:/data/people.json")
    val df = spark.read.format("json").load("file:///d:/data/people.json")
    df.show()
    df.printSchema()
    df.select("name").show()
    df.select($"name", $"age" + 1).show()
    df.filter($"age" > 21).show()
    df.groupBy("age").count().show()
    //
    //    //createOrReplaceTempView创建临时视图
    df.createTempView("people")
    //    df.createGlobalTempView("people")  global_temp
    val sqlDF = spark.sql("SELECT name,age+1 as plusAge FROM people")
    //    sqlDF.show()
    //
    //    /**
    //     * 什么才会需要创建一个新的SparkSession呢？
    //     * 		当不希望使用之前SparkSession的一些配置信息、临时表、注册的自定义函数的时候
    //     * 		但是新的SparkSession共享底层的SparkSession、缓存数据
    //     */
    //    val spark1 = spark.newSession()
    //    spark1.sql("SELECT * FROM global_temp.people")
    ////    spark.sql("SET -v").show(numRows = 2000, truncate = false)
    //     sqlDF.write.mode(SaveMode.Overwrite).format("json").save("file:///d:/data/abc")
  }
}
