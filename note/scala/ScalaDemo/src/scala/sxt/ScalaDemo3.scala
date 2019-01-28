package scala.sxt

object ScalaDemo3 {

  def main(args: Array[String]): Unit = {
    println(add(1)(2))
    println(addMore(1,2,3))
    fun()
    println(strConcat("anglebaby","weijianlin"))
    println(fun2(((a : Int) => a * 100),100))
    println(fun3(1)(2,3))
  }
  /*
  * 多个参数列表
  * */
  def add(num:Int)(num2:Int)= num + num2

  /**
    * 可变参数个数的函数
    * String...  strs
    */
  def addMore(nums :Int*):Int={
    var sum = 0
    for(ele <- nums){
      sum += ele
    }
    sum
  }
  /**
    * 匿名函数
    * 	：没有名字的函数
    */
  val fun = () => {println("angle baby")}

  //有参数匿名函数  匿名函数是无法显示的制定返回值类型的
  var strConcat = (a:String,b:String) => a + "--" + b

  /**
    * 高阶函数：
    * 	1、函数的参数是函数  fun1(fun2)   面向函数编程
    * 	2、函数的返回是函数
    */

  /**
    * fun6函数的参数有两个
    * 	第一个参数：函数   函数的格式是 只有一个参数并且返回值是Int
    * 	第二个参数：Int类型的值
    * Int => Int表示的是 第一个参数(函数)的格式
    */
  def fun2(ff : Int => Int,num :Int) : Int={
    ff(num)
  }
  /**
    * 返回值是函数（函数参数有个，函数返回值是Int）
    */
  def fun3(num:Int):(Int,Int)=>Int ={
    def fun4(num1:Int,num2:Int):Int={
      num + num1 + num2
    }
    fun4
  }
}
