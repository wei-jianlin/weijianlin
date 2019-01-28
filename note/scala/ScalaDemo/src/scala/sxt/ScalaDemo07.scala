package scala.sxt

/**
 * Scala的隐式转换系统
 * 	隐式转换，在编写程序的时候可以尽量少的去编写代码，
 * 	让编译器去尝试在编译期间自动推导出这些信息来，
 * 	这种特性可以极大的减少代码量，提高代码质量
 * 包含：
 * 	隐式值
 * 	隐式视图
 * 	隐式类
 */
object ScalaDemo07 {
  def main(args: Array[String]): Unit = {
   
    
   
   /**
    * 隐式值
    * 隐式转换必须满足无歧义规则
    * 
    * 声明隐式参数的类型最好是自定义的数据类型，不要使用Int,String这些常用类型，防止碰巧冲突
    */
    implicit val num1 = 1000
//    implicit val num2 = 2000
    add
    
    
    /**
     * type mismatch; found : String("1000") required: Int
     * 工作原理：
     * 		在调用sayNum的时候传入的是字符串  与sayNum定义的参数不一致
     * 		此时他会在作用域内寻找隐式视图，发现了stringToInt(参数是string，输出是Int)
     * 		stringToInt("1000") = 1000
     * 		sayNum(1000)
     */
    implicit def stringToInt(numStr : String) = Integer.parseInt(numStr)
    sayNum("1000")
    
    
    
    val s1 = new Student1()
    val s2 = new Student2()
    implicit def s2Tos1(s:Student2) = new Student1()
    s2.sayStudent("Anagelababy")
    
    /**
     * 隐式类
     * 工作原理：
     * 		"abc"这个字符串在调用getLength方法的时候，发现没有这个方法，此时会在作用域内寻找隐式视图或者隐式类
     * 		StringUtil隐式类,构造函数是String类型并且就一个参数，此时会将会创建StringUtil这个类的对象，将"abc"
     * 		传入给StringUtil构造函数====StringUtil类型的对象，getLength方法
     */
    implicit class StringUtil(str:String){
      def getLength() = str.length()
    }
    "abc".getLength
  }
  
   def add(implicit num:Int) = {
      println(num + 1000)
    }
  
   /**
    * 隐式视图
    * 	隐式转换为目标类型：把一种类型自动转换到另一种类型
    */
   def sayNum(num:Int) = {
     println("say num:" + num)
   }
} 


class Student1{
  def sayStudent(name:String) = {
    println(name)
  }
}
class Student2{
  
}