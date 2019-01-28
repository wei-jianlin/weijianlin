package scala.org

object CaseClass {
  /**
    * case class适用于对不可变数据进行建模
    * 使用参数创建案例类时，参数是公共val的
    */
  def main(args: Array[String]): Unit = {
    val message = Message("I","love","beautiful girl")
    println(message.body)
    //message.body = "1"  // this line does not compile
    //案例类按值行比较,而不是引用
    val message2 = Message("I","love","beautiful girl")
    println(message == message2)
  }
}
case class Message(sender: String, recipient: String, body: String){

}
