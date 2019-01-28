package scala.org

object 传名参数 {

  def main(args: Array[String]): Unit = {
    var i = 2

    whileLoop (i > 0) {
      println(i)
      i -= 1
    }
  }

  //要将一个参数变为传名参数，只需在它的类型前加上 =>
  //传名参数的优点是，如果它们在函数体中未被使用，则不会对它们进行求值
  //另一方面，传值参数的优点是它们仅被计算一次
  def calculate(input: => Int) = input * 37

  def whileLoop(condition: => Boolean)(body: => Unit): Unit =
    if (condition) {
      whileLoop(condition)(body)
    }
}
