package spark.sql.sxt

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Encoders

object ReadCollects {
  def ReadCollects(args: Array[String]): Unit = {
     val spark = SparkSession
      .builder()
      .appName("ReadCollects")
      .master("local")
      .getOrCreate()
      //这里面封装了大量的Encoder编码器，当类型需要编码器会自动去寻找隐式编码器
    import spark.implicits._
    
    /**
     * 实际这种方式适合于测试环境
     */
    val caseClass = Seq(Person("Andy", 32),Person("jack", 12))
    val caseClassDS = spark.createDataset(caseClass)
    caseClassDS.createTempView("clazz")
//    val caseClassDS = caseClass.toDF()
    caseClassDS.show()
//    
//    val peopleDS = spark.read.json("file:///d:/data/people.json").as[Person]
//    peopleDS.createTempView("abc")
//    spark.sql("select name from abc").show
//    peopleDS.show()
  }
}
//样例类
case class Person(name: String, age: Long)