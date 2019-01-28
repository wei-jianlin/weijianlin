package scala.org


object ImplicitTest {

  abstract class  Monoid[A]{
    def add(a : A,b : A) : A
    def unit : A
  }

  implicit val stringMonoid :Monoid[String] = new Monoid[String]{
      def add(a:String,b:String):String = a.concat(b)
      def unit : String = ""
  }

  implicit val intMonoid :Monoid[Int] = new Monoid[Int]{
      def add(a : Int,b : Int) : Int = a + b
      def unit :Int = 0
  }

  def sum[A](sx : List[A])(implicit m : Monoid[A]) : A = {
    if(sx.isEmpty) m.unit
    else m.add(sx.head,sum(sx.tail))
  }

  def main(args: Array[String]): Unit = {
    var a = List(1,2,3)
    println(a.head)
    println(a.tail)
    println(sum(List(1, 2, 3)))       // uses IntMonoid implicitly
    println(sum(List("a", "b", "c")))
  }

}
