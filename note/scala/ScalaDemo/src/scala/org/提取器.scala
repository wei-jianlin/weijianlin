package scala.org

object 提取器 {

  def main(args: Array[String]): Unit = {
    //解构
    val a = List(1)
    val List(args) = a
    println(args)
    val b = List(1,2,3)
    val List(c:Int) = b
    println(c)
  }
}
