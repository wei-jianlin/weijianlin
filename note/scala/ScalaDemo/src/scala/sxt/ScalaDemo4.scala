package scala.sxt

object ScalaDemo4 {

  def main(args: Array[String]): Unit = {
    val str1 = "hello sxt"
    var str2 = "hello SXT"
    println(str1.indexOf("b"))
    println(str1.compareToIgnoreCase(str2))
    var stringbuilder = new StringBuilder
    stringbuilder.append("abc")
    stringbuilder.+('d')
    stringbuilder + 'e'
    stringbuilder.++=("fg")
    stringbuilder ++= "hi"
    println(stringbuilder)

    /**
      * 数组
      * 创建了一个长度为10，并且泛型为Int的空数组
      */
    var array = new Array[Int](10)
    for(i <- 0 until array.size){
      array(i) = i
    }
    for(i <- 0 until array.length){
      println(array(i))
    }
    (Array.fill(5))("bjsxt").foreach { x => println(x) }
  }
}
