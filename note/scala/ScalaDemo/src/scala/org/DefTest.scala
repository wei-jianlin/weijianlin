package scala.org

object DefTest {

  def main(args: Array[String]): Unit = {
    println(new People().username)
    println(new People().getUserName)
  }
}

trait User{
  def username():String

  def getUserName:String
}

class People extends User{
  var username:String = "1"

  def getUserName():String = "2"
}