package scala.sxt

import scala.Tuple22


object ScalaDemo05 {
    def main(args: Array[String]): Unit = {
      //set集合    去重数据
      val set1 = Set(1,2,3,4,4)
      val set2 = Set(1,2,5)
      //遍历
      //注意：set会自动去重
      set1.foreach { println}
     for(s <- set1){
        println(s)
      }
      println("*******")
     /**
      * 方法举例
      */
      
     //交集    &==intersect
     val set3 = set1.intersect(set2)
     set3.foreach{println}
     val set4 = set1.&(set2)
     set4.foreach{println}
     println("*******")
     //差集  diff = &~
     set1.diff(set2).foreach { println }
     set1.&~(set2).foreach { println }
     //子集
     set1.subsetOf(set2)
     
     //最大值
     println(set1.max)
     //最小值
     println(set1.min)
     println("****")
      
      println(set1.mkString("\t"))
      
      //(2,"shsxt")这是一个二元组类型    用一堆小括号组织起来的元素，
      val map = Map(1 -> "bjsfanbingbingxt",(2,"shsxt"),"5" -> "sasxt")
      //Option类型有两个子类型   Some None   如果通过key能够获取到value此时返回的是Some  否则返回的是None
      //getOrElse如果能获取到值，那么将值返回，否则返回的是默认值  get  getOrElse
      println(map.get("5").getOrElse("no result"))
      map.foreach((x:(Any,String)) => {
        println(x._2)
      })
      
        val map1 = Map(
      (1,"a"),    
      (2,"b"),    
      (3,"c")    
    )
    val map2 = Map(
      (1,"aa"),
      (2,"bb"),
      (2,90),
      (4,22),
      (4,"dd")
    )
    map1.++:(map2).foreach(println)
    
    println(map.contains(6))
    
    println(map.exists(x=>x._2.contains("fanbingbing")))
    
    
    /**
     * 元组
			1.元组定义
				 与列表不同的是元组可以包含不同类型的元素。元祖对象是通过一对小括号包裹起来组成的
     */
    val t2 = Tuple2(1,"libingbing")
    t2._1
    t2._2
    val tt2 = (1,"lichen") 
    tt2._1
    tt2._2
    
    val t22 =  (1,2,4,5,29,3,4,5,6)
    val t3 = t2.swap
    println(t3._1 + "===" + t3._2)
    
     
     /**
      * trait	特性
            1.概念理解
            Scala Trait(特征) 相当于 Java 的接口，实际上它比接口还功能强大。
            与接口不同的是，它还可以定义属性和方法的实现。
            一般情况下Scala的类可以继承多个Trait，从结果来看就是实现了多重继承。Trait(特征) 定义的方式与类类似，但它使用的关键字是 trait。
            2.举例：trait中带属性带方法实现
          		  注意：
            继承的多个trait中如果有同名的方法和属性，必须要在类中使用“override”重新定义。
            trait中不可以传参数
      */
    
     val p = new Person1 
     p.read("Fenger")
     p.listen("Fenger Love you")
     
     
    val p1 = new Point(2,2)
    val p2 = new Point(1,3)
    println(p1.isEqule(p2))
    println(p1.isNotEqule(p2))
    }  
}

trait Read {
  val readType = "Read"
  val gender = "m"
  def read(name:String){
  	println(name+" is reading")
  }
}

trait Listen {
  val listenType = "Listen"
  val gender = "m"
  def listen(name:String){
	  println(name + " is listenning")
  }
}

class Person1() extends Read with Listen{
  override val gender = "f"
}


trait Equle{
  def isEqule(x:Any) :Boolean 
  def isNotEqule(x : Any)  = {
    !isEqule(x)
  }
}

class Point(x:Int, y:Int) extends Equle {
  val xx = x
  val yy = y

  def isEqule(p:Any) = {
    p.isInstanceOf[Point] && p.asInstanceOf[Point].xx==xx
  }
  
}



 