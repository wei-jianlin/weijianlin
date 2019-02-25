package spark.sql.me

import org.apache.spark.sql.SparkSession

object ReadJsonFile {

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("SparkSql").master("local").getOrCreate()
    val dataFrame = sparkSession.read.format("json").load("file:///C:\\access.log")
/*    dataFrame.show()
    dataFrame.printSchema()
    dataFrame.select("name").show()
    dataFrame.groupBy("age").count().show()*/
    dataFrame.createTempView("people")
    val people = sparkSession.sql("select * from people where age > 18")
    people.createTempView("people2")
    sparkSession.sql("select p.*,p2.* from people p left join people2 p2 on p.age = p2.age").show()
    sparkSession.close()
  }
}
