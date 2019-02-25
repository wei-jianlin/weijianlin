package spark.sql.sxt

import org.apache.spark.sql.SparkSession

object RDD2DSStatic {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("RDD2DSStatic")
      .master("local")
      .getOrCreate()
      
    import spark.implicits._  
    
    /**
     * 从people.txt刚读出来的RDD  每一条数据类型都是String,String对象中没有描述每一个字段名称以及类型
     * 如果想要将RDD转成DS，那么需要将String对象封装到一个对象中，这样DS才能知道每一个字段对应的列名字以及类型
     */
    
    val PersonRDD = spark.sparkContext
            .textFile("file:///C:\\702\\尚学堂大数据\\大数据二期\\Spark-14\\spark sql core+data\\spark sql data\\data\\people.txt")
            .map(_.split(","))
            .map(attributes => Person(attributes(0), attributes(1).trim.toInt))
    /**
     * Case class 定义了表的 Schema.Case class 的参数名使用反射读取并且成为了列名            
     */
    val peopleDF = PersonRDD.toDF()
    peopleDF.createOrReplaceTempView("people")
    val teenagersDF = spark.sql("SELECT name, age FROM people WHERE age BETWEEN 13 AND 19")
    teenagersDF.map(teenager => "Name: " + teenager(0)).show()
    teenagersDF.map(teenager => "Name: " + teenager.getAs[String]("name")).show()
    
    /**
     *     teenager.getValuesMap输出结果作为NADS这个DataSet的数据
     *     这个过程需要一个编码器，在Spark的隐式转换中没有这个编码器，所以需要自己来创建一个编码器
     */
    implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[Map[String, Any]]
    val NADS = teenagersDF.map(teenager => teenager.getValuesMap[Any](List("name", "age")))
    NADS.foreach(x=>{
      val keys = x.keys
      for(k <- keys){
        println(k + "===" + x.get(k))
      }
    })
    NADS.collect()
  }
}
