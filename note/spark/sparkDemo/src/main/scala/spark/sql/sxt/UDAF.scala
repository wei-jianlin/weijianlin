package spark.sql.sxt

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.MutableAggregationBuffer
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction
import org.apache.spark.sql.types.DataType
import org.apache.spark.sql.types.DoubleType
import org.apache.spark.sql.types.LongType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.SparkSession

object UDAF {
  def main(args: Array[String]): Unit = {
     val spark = SparkSession
      .builder()
      .appName("RDD2DS")
      .master("local")
      .getOrCreate()
      
    import spark.implicits._  
    spark.udf.register("myAverage", new UserDAF)

    val df = spark.read.json("file:///d:/data/employees.json")
    df.createOrReplaceTempView("employees")
    df.show()
    df.printSchema()
    
    val result = spark.sql("SELECT myAverage(salary) as average_salary FROM employees")
    result.show()
  }
}

class UserDAF extends UserDefinedAggregateFunction {
  //输入数据的类型
  def inputSchema: StructType = StructType(StructField("inputColumn", LongType) :: Nil)

  
  
  // 初始化一个buffer，实际上这个buffer就是一个row对象，具备更新、根据索引查找等特性   创建了两个buffer
  def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = 0L
    buffer(1) = 0L
  }
  // 将输入数据更新到buffer中
  def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    if (!input.isNullAt(0)) {
      buffer(0) = buffer.getLong(0) + input.getLong(0)
      buffer(1) = buffer.getLong(1) + 1
    }
  }
  
  //在shuffle过程中计算的数据类型
  def bufferSchema: StructType = {
    StructType(StructField("sum", LongType) :: StructField("count", LongType) :: Nil)
  }

  //最终返回结果的数据类型
  def dataType: DataType = DoubleType
  // 函数是否在相同的输入上返回相同的输出
  def deterministic: Boolean = true

  //reduce端的大合并
  def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer1.getLong(0) + buffer2.getLong(0)
    buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
  }
  // 返回最终结果
  def evaluate(buffer: Row): Double = buffer.getLong(0).toDouble / buffer.getLong(1)
}