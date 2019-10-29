object Main extends {
  // Костыль для работы переменной table, чтобы вытащить её из области видимости case (строки 16 и 22)
  var table = (v1: Int) => (v1: Int) => 0
  var procedure = (v1: Int) => (v1: Double) => "Костыль для 3 задания: строки 38 и 42"

  def main(args: Array[String]): Unit = { // Основной метод класса, консольный интерфейс
    println("Hello! What exercise want you do?\n1 - partApply\n2 - compose\n3 - curry")

    readInt() match { // по цифре выбирается номер задания
      case 1 => { // 1 задание: Небольшой симулятор SQL-запроса :))))
        println("SQL-simulator: what operation want you do?\n1 - Select\n2 - Update\n3 - Delete\n4 - Insert")
        readInt() match {
          case 1 => table = partApply(select, _)
          case 2 => table = partApply(update, _)
          case 3 => table = partApply(delete, _)
          case 4 => table = partApply(insert, _)
        }
        println("Input row limit")
        val limit = readInt()
        val query = table(limit)

      }
      case 2 => { // 2 задание: композ в обратную сторону не получается из-за конфликта типов, так что только gf(
        println("Input integer number")
        val num = readInt()
        val selection = println("compose or andThen?\n1 - compose\n2 - andThen")
        readInt() match {
          case 1 => fg(num)
          case 2 => gf(num)
        }
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
    def endQuery(a: Int, b: Int): Int = a / b

    println("Next query was executed:")
    val result = f(2, 3)
    print(f" LIMIT $x ")
    print(f"WHERE value = ")

    endQuery(result, _)
  }

  // Методы для 1 задания
  // типВыборка
  def select(db: Int, table: Int): Int = {
    print(f"SELECT FROM $db.$table")
    db * table
  }

  // типРедактирование
  def update(db: Int, table: Int): Int = {
    print(f"UPDATE $db.$table")
    db + table
  }

  // типУдаление
  def delete(db: Int, table: Int): Int = {
    print(f"DELETE FROM $db.$table")
    math.round(math.random()).toInt
  }

  // типДобавление
  def insert(db: Int, table: Int): Int = {
    print(f"INSERT INTO $db.$table")
    math.pow(db + table, 0).toInt
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
  val fg = g _ compose f
  val gf = f _ andThen g


}
