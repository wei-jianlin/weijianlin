package scala.sxt

object ScalaDemo1 {

  def main(args: Array[String]): Unit = {
    val a = 10
    val p = Person("fangbingbing",19)
    println(p.name)
    println(p.age)
    var p3 = new Person("fangbingbing","licheng",20)
    p3.sayLove()
    Person.sayName()
  }
}

/**类默认的构造函数是无参数
  * 有参数构造函数
  * 创建多个构造函数
  */
class Person(var n:String,var a :Int){
    val name = n
    var age = a

    //重写的构造函数体内必须调用之前的构造函数
    def this(a :String,b : String,c : Int)={
      this(a,c)
      println("新的构造函数")
    }
    def sayLove()={
      println("I love " + name)
    }
}
/** 对象是一个只有一个实例的类。它被引用时被懒加载
  * object Person是peroson类的伴生对象，在这个对象中所声明一切属性都是静态的
  * 所以我们在创建main函数的时候，将main函数放在伴生对象中
  *
  * 伴生对象还提供了apply方方法  方便创建或者写复杂的代码
  */
object Person{
  def apply(a : String, b : Int) ={
    new Person(a,b)
  }
  def sayName(): Unit ={
    println("anglebaby")
  }
}