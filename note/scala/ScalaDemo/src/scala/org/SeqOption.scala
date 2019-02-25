package scala.org

object SeqOption {

  def main(args: Array[String]): Unit = {
    var a = List(2)
    //向队列的头部追加数据，创造新的列表
    a = 1 :: a
    a.foreach(println)
    println("::")
    //用于在尾部追加元素
    a = a :+ 3
    a.foreach(println)
    println(":+")
    //在头部追加元素，和::很类似，但是::可以用于pattern match ，而+:则不行
    //关于+:和:+,只要记住冒号永远靠近集合类型就OK了
    a = 0 +: a
    a.foreach(println)
    println("+:")
    var b = List(4,5,6)
    //++连接两个集合,::: 该方法只能用于连接两个List类型的集合
    //b = b ++ a
    b = a ::: b
    b.foreach(println)
    println("++")
  }
}
case class Friend(a : String,b : String) extends Comparable[Friend]{
  override def compareTo(o: Friend): Int = {
    if(this.a.equals(o.a) && this.b.equals(o.b)) return 0
    if(this.a.equals(o.b) && this.b.equals(o.a)) return 0
    1
  }
}