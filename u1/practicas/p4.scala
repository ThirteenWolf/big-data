// Fibonacci
import math.{pow, sqrt}

object Practica4 {  
  def main(args: Array[String]): Unit =
    println("1: " + fib1(20))
    println("2: " + fib2(20))
    println("3: " + fib3(20))
    println("4: " + fib4(20))
    println("5: " + fib5(20))
  
  def fib1(n: Int): Int = {
      if(n<2) n
      else fib1(n-1) + fib1(n-2)
  }

  def fib2(n: Int): Double = {
      if(n<2) n
      else {
          val a = ((1 + sqrt(5)) / 2)
          val b = ((pow(a, n) - pow(1-a, n)) / (sqrt(5)))
          b
      }
  }

  def fib3(n:Int): Int = {
      var a: Int = 0
      var b: Int = 1
      var c: Int = 0

      for(i <- 1 to n){
          c = b + a
          a = b
          b = c
      }
      a
  }

  def fib4(n: Int): Int = {
      var a: Int = 0
      var b: Int = 1
      for(i <- 1 to n)
      {
          b = b + a
          a = b - a
      }

      a
  }

  def fib5(n: Int): Int = {
      if(n < 2) n
      else {
          val arreglo = new Array[Int](n + 1)
          arreglo(0) = 0
          arreglo(1) = 1

          for(i <- 2 to n) arreglo(i) = arreglo(i - 1) + arreglo(i - 2)

          arreglo(n)
      }
  }

  def fib(n: Int): Int = {
    @annotation.tailrec
    def loop(n: Int, prev: Int, curr: Int): Int = 
      if(n == 0) prev
      else loop(n-1, curr, curr + prev)
    loop(n, 0, 1)
  }
}