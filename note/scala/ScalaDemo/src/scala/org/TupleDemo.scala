package scala.org

object TupleDemo {

  def main(args: Array[String]): Unit = {

    /**元组是一个可以容纳不同类型元素的类,元组是不可变的,
      * 当我们需要从函数返回多个值时，元组会派上用场
      */
    val a = ("元祖","1")
    val b : Tuple3[String,Char,Int]= ("a",'b',1)
    println(a._1)
    println(b._3)
    //Scala 元组也支持解构
    val (name,age) = a
    println(name + "_" + age)

    val planetDistanceFromSun = List(("Mercury", 57.9), ("Venus", 108.2), ("Earth", 149.6 ), ("Mars", 227.9), ("Jupiter", 778.3))

    planetDistanceFromSun.foreach( tuple => println(tuple._1 + "-" + tuple._2))

    planetDistanceFromSun.foreach{ tuple => {

      tuple match {

        case ("Mercury", distance) => println(s"Mercury is $distance millions km far from Sun")

        case p if(p._1 == "Venus") => println(s"Venus is ${p._2} millions km far from Sun")

        case p if(p._1 == "Earth") => println(s"Blue planet is ${p._2} millions km far from Sun")

        case _ => println("Too far....")

      }

    }

    }
  }
}
