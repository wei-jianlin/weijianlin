package scala.org

object HighFunctions {

  def main(args: Array[String]): Unit = {
    val a = List(1,2,3)
    var b = a.map(_ * 2)
    b.foreach(println)

  }
}
