package scala.org

object ClassDemo {

  def main(args: Array[String]): Unit = {
    val point = new Point
    point.x = 99
    point.y = 101 // prints the warning
    val point2 = new Point2(1,2)
    point2.x = 99
    //point2.y = 101 // does not compile
    val point3 = new Point3(3,4)
    //point3.x x is privated
  }
}
class Point{
  private var _x = 0
  private var _y = 0
  private val bound = 100

  def x = _x
  def x_= (newValue: Int): Unit = {
    println(newValue)
    if (newValue < bound) _x = newValue else printWarning
  }
  def y = _y
  def y_= (newValue: Int): Unit = {
    if (newValue < bound) _y = newValue else printWarning
  }
  private def printWarning = println("WARNING: Out of bounds")

}

class Point2(var x : Int,val y :Int){
}

class Point3(x : Int,y :Int){
}