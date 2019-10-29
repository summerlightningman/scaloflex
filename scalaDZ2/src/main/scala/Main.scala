object Main extends {
  // Костыль для работы переменной table, чтобы вытащить её из области видимости case (строки 16 и 22)
  var table = (v1: Int) => (v1: Int) => 0
  var procedure = (v1: Int) => (v1: Double) => "Костыль для 3 задания: строки 38 и 42"

  def main(args: Array[String]): Unit = { // Основной метод класса, консольный интерфейс
    println("Hello! What exercise want you do?\n1 - partApply\n2 - compose\n3 - curry")

    readInt() match { // по цифре выбирается номер задания
      case 1 => { // 1 задание: Небольшой симулятор SQL-запроса :))))
        val partial = partApply(_ + _, 2)
        val result = partial(0)
        println(result)
      }
      case 2 => {
        fg(10)
      }
      case 3 => { // 3 задание: каррирование: есть 2 метода, с которыми работает функция: middle и absReduce
        println("What operation want you do?\n1 - Middle arithmetic\n2 - Absolute reduce")
        readInt() match {
          case 1 => procedure = curry(middle)
          case 2 => procedure = curry(absReduce)
        }
        println("Input numbers with space separator")
        val nums = readLine().split(" ")
        print(procedure(nums(0).toInt)(nums(1).toDouble))
      }
    }
  }

  // 1 задание
  def partApply(f: (Int, Int) => Int, x: Int): Int => Int = {
    f(_, 2)
  }


  // 3 задание
  def curry(f: (Int, Double) => String): Int => Double => String =
    (b: Int) => f(b, _)

  // метод среднее арифмитическое для 3 задания
  def middle(v1: Int, v2: Double): String = {
    val result: Double = (v1 + v2) / 2
    f"Middle number between $v1 and $v2 = $result"
  }

  // метод модуль разности для 3 задания
  def absReduce(v1: Int, v2: Double): String = {
    val result: Double = math.abs(v1 - v2)
    f"Absolute reduce of $v1 and $v2 = $result"
  }

  // методы f и g для 2 задания
  def f(x: Int): Double = x.toDouble

  def g(y: Double): String = y.toString

  // продолжение 2 задания
  val fg = f _ andThen g


}
