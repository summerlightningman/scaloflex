package zadanie2

import scala.io.Source
import scala.io.StdIn._
import scala.util.control.Breaks._

object Main {
  def main(args: Array[String]): Unit = {

    println("Greetings! What exercise are you want to execute?") // главное меню
    println("1 - Simple numbers") // при нажатии на 1 - поиск простого числа
    println("2 - Fibonacci numbers") // 2 - вывод чисел Фибоначчи
    println("3 - All number replaces") // 3 - вывод все перестановок чисел заданных через пробел
    println("4 - All number inserts")
    println("5 - Cargo-remain calculator") // 5 - рассчёт остатка груза на складе


    readInt match {
      case 1 =>
        simpleNums(readLine("What number, simple numbers will be found until?").toInt)
      case 2 =>
        fibonacci(readLine("What number, Fibonacci numbers will be found until").toInt)
      case 3 =>
        numReplace(readLine("Input array with space separators"))
      case 4 =>
        numPlaces(readLine("Input array with space separators"))
      case 5 =>
        calculateThePackage
    }
  }


  def simpleNums(n: Int): Unit = {
    for (i <- 1 to n)
      breakable {
        for (j <- 2 until i)
          if (i % j == 0) {
            print(s"$i, ")
            break
          }
      }
  }


  def fibonacci(i: Int): Unit = {
    val first = 0;
    val second = 1;
    print(s"$first, ")

    def go(first: Int, second: Int): Unit = {
      print(s"$second, ")
      if (i > second)
        go(second, first + second)
    }

    go(first, second)
  }


  def numReplace(arr: String): Unit = {
    val nums = arr.split(" ")
    var middle = ""
    val row = nums

    for (i <- 0 until nums.length) {
      for (j <- 0 until nums.length) {
        middle = row(i)
        row(i) = row(j)
        row(j) = middle

        println(row.mkString(" "))
      }
    }
  }

  def numPlaces(arr: String): Unit = {
    val nums = arr.split(" ")

    for (i <- 0 until nums.length)
      for (j <- 0 until nums.length)
        if (i != j) println(nums(i) + " " + nums(j))
  }

  def calculateThePackage(): Unit = {
    val info = Source.fromFile(s"${System.getProperty("user.dir")}/assets/package.txt")
    val total = info.getLines.map(_.toInt).sum

    println(s"Cargo balance: $total")
  }
}