object Main extends {
    // Костыль для работы переменной table не только в поле case (строки 15 и 22)
  var table = (v1: Int) => (v1: Int) => 0

  def main(args: Array[String]): Unit = {
    println("Hello! What exercise want you do?")
    println("1 - partApply")
    println("2 - composes")
    println("3 - curry")
    readInt() match {
      case 1 => {         // Небольшой симулятор SQL-запроса :)))) 1 задание
        println("SQL-simulator: what operation want you do?\n1 - select\n2 - update\n3 - delete\n4 - insert")
        readInt() match {
          case 1 => table = partApply(select, _)
          case 2 => table = partApply(update, _)
          case 3 => table = partApply(delete, _)
          case 4 => table = partApply(insert, _)
        }
        println("Input row limit")
        val limit = readInt()
        val query = table(limit)
        print(query)
      }
      case 2 => {       // 2 задание: композ в обратную сторону не получается из-за конфликта типов, так что только gf(
        println("Input integer number")
        val num = readInt()
        val selection = println("compose or andThen?\n1 - compose\n2 - andThen")
        readInt() match {
          case 1 => fg(num)
          case 2 => gf(num)
        }
      }
      case 3 => ???
    }
  }

  def partApply(f: (Int, Int) => Int, x: Int): Int => Int = {
    println("Next query was executed:")
    f(2, 3)
    print(f" LIMIT $x ")
    println(f"WHERE value = ")
    _ * 1
  }

  def f(x: Int): Double = x.toDouble

  def g(y: Double): String = y.toString

  val fg = g _ compose f
  val gf = f _ andThen g

  def select(db: Int, table: Int): Int = {
    print(f"SELECT FROM $db.$table")
    db * table
  }

  def update(db: Int, table: Int): Int = {
    print(f"UPDATE $db.$table")
    db + table
  }

  def delete(db: Int, table: Int): Int = {
    print(f"DELETE FROM $db.$table")
    math.round(math.random()).toInt
  }

  def insert(db: Int, table: Int): Int = {
    print(f"INSERT INTO $db.$table")
    math.pow(db + table, 0).toInt
  }
}
