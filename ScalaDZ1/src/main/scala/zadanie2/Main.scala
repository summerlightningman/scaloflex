package zadanie2

import java.io.{File, FileInputStream}
import java.util.Scanner

object Main {
  def main(args: Array[String]): Unit = {
    println("Greetings! What exercise are you want to execute?") // главное меню
    println("1 - Simple numbers") // при нажатии на 1 - поиск простого числа
    println("2 - Fibonacci numbers") // 2 - вывод чисел Фибоначчи
    println("3 - All number replaces") // 3 - вывод все перестановок чисел заданных через пробел
    println("4 - All number inserts")
    println("5 - Cargo-remain calculator") // 5 - рассчёт остатка груза на складе

    val selection = readInt()
    selection match {
      case 1 =>
        println("What number, simple numbers will be found until?")
        simpleNums(readInt())
      case 2 =>
        println("What number, Fibonacci numbers will be found until")
        fibonacci(readInt())
      case 3 =>
        println("Input array with space separators")
        numReplace(readLine())
      case 4 =>
        println("Input array with space separators")
        numPlaces(readLine())
      case 5 =>
        calculateThePackage()
    }
  }

  // Простые числа
  def simpleNums(n: Int): Unit = {
    var counter: Int = 0 // счётчик

    for (i <- 1 to n) { // проходимся по всем числам
      counter = 0 // обнуляем счётчик найденных делителей при новых числах
      for (j <- 2 to i) { // делим все числа, которые меньше итерируемого
        if (i % j == 0) // если находтся делитель
          counter += 1 // то инкрементируем счётчик делителей
      }
      if (counter == 1) // если делитель один - само число
        print(s"$i, ") // то выводим его
    }
  }

  // Немножко функционального программирования - число Фибоначчи
  def fibonacci(i: Int): Unit = {
    val first = 0; // первые и вторые числа зададим статически
    val second = 1;
    print(s"$first, ") // выведем первое число

    def go(first: Int, second: Int): Unit = { // получаем следующие числа
      print(s"$second, ") // выводим последнее полученное число
      if (i > second) // если не достигли выбранного числа, вызываем новый метод
        go(second, first + second)
    }

    go(first, second) // запускаем рекурсирующий метод
  }

  // кривовато работает ><
  def numReplace(arr: String): Unit = {
    val nums = arr.split(" ")
    var middle = ""
    val row = nums

    for (i <- 0 until nums.length) {
      for (j <- 0 until nums.length) {
        middle = row(i)
        row(i) = row(j)
        row(j) = middle

        printArr(row)
      }
    }

    // печать массива
    def printArr(row: Array[String]): Unit = {
      for (num <- nums) print(s"$num ")

      println()
    }
  }

  // подстановки чисел, надеюсь правильно понял задание
  def numPlaces(arr: String): Unit = {
    val nums = arr.split(" ")

    for (i <- 0 until nums.length)
      for (j <- 0 until nums.length)
        if (i != j) println(nums(i) + " " + nums(j))
  }

  // расчёт остатка груза на складе
  def calculateThePackage(): Unit = {
    val info = new Scanner(new File("./assets/package.txt")) // файл в папке assets
    var total = 0 // общий итог

    while (info.hasNextLine) // считывание строк
      total += Integer.parseInt(info.nextLine()) // подсчёт остатка груза

    println(s"Cargo balance: $total") // вывод результатов вычисления
  }
}

