package scala.sxt

object ScalaDemo2 {

  def main(args: Array[String]): Unit = {
    println(1 to 10)
    println(1.to(10,2))
    println(1 until 10)
    println(1.until(10,3))
    ////统计1 - 100的和
    var sum = 0
    for(i <- 1 to 100){
      sum += i
    }
    println(sum)
    //统计50以内所有偶数的和
    sum = 0
    for(ele <- 1 to 100;if ele % 2 == 0 && ele <= 50){
      sum += ele
    }
    println(sum)
    /**
      * 多层for循环
      */
    //打印九九乘法表
     for(i <- 1 to 9){
       for(j <- 1 to i){
         print(j + "*" + i + "=" + j * i + " ")
       }
       println()
     }
    for(i <- 1 to 9 ; j <- 1 to i){
      print(j + "*" + i + "=" + i*j + "\t")
      if(j == i)
        println()
    }
    /*
     * 将1 - 100的偶数放在一个集合中保存
     * yield就是将符合条件的元素封装到一个集合中
     * oss集合
     */
    val oss = for(i <- 1 to 100;if i % 2 == 0) yield i
    for(i <- oss)println(i)

    def add(a : Int,b : Int)= {
      a + b
    }
    println(add(1,2))

    /**
      * 递归函数：
      *  在函数体又调用了自己本身
      *  计算10的阶乘
      *  10 * 9 * ...* 1
      *  为什么递归函数必须声明函数的返回值类型呢？
      */
    def fun(num:Int): Int ={
      if(num == 1){
         num
      }else{
        num * fun(num - 1)
      }
    }
    println(fun(3))

    /**
      * 包含参数默认值的函数
      * fun3函数有两个参数
      * 	n1:默认值
      * 	n2:需要用户传入的
      */
    def fun2(n1 : Int = 10,n2 : Int) ={
      n1 + n2
    }
    println(fun2(n2 = 5))
    println(fun2(5,5))

    def fun3(n1 : Int,n2 : Int = 10) ={
      n1 + n2
    }
    println(fun3(5))
  }
}
