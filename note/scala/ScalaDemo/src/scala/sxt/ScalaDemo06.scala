package scala.sxt

object ScalaDemo06 {
  def main(args: Array[String]): Unit = {
//    val list = List(1,2,3,4,5,6)
    val num = 10
    
    //在scala中 match 既可以进行值匹配  又可以类型匹配
    /**
     * 在scala中 match根据顺序从上往下去匹配，一旦匹配成功就不再继续往下匹配了
     */
    matchVal(num)
    
    val list = List(1,2,3,45,9)
    list.foreach { matchVal }
    
    val ppp1 = new PPP("Angelababy",29)
    val ppp2 = PPP("Dilireba",26)
    val ppp3 = PPP("zhangxinyi",22)
    val ppp4 = PPP("fanbingbing",20)
    val ppp5 = PPP("liuyan",28)
    val pppList = List(ppp1,ppp2,ppp3,ppp4,ppp5)
    
    pppList.foreach { x => x match {
      case PPP("zhangxinyi",22) => println("value match" + x.toString())
      case p:PPP => println("type match" + p.toString()) 
      case _ => println(x.toString())
    }}
    
  }
  
  /**
   * scala.MatchError
   * 匹配失败
   */
  def matchVal(num:Any) = {
    num match {
      case i:Int => println("Int:" + i)
      case 10 => println(10) 
      case 9 => println(9)
      case _ => println("default")
    }
  }
}

/**
 * 样例类(case classes)
 * 使用了case关键字的类定义就是样例类(case classes)，样例类是种特殊的类
 * 特点：
 * 		1、样例类默认帮你实现了toString,equals，copy和hashCode、apply等方法
 * 		2、样例类可以new, 也可以不用new
 */
case class PPP(name:String,age:Int) {
  
}









