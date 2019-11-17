package zadanie2_4

import java.sql.Timestamp

object Main extends App {
  def separate(): Unit = println("__________________________")

  val operations: List[BankOperation] = List[BankOperation](
    BankOperation(Timestamp.valueOf("2019-04-03 19:42:00.333"), 1, 340, "cinema", 5),
    BankOperation(Timestamp.valueOf("2019-03-20 12:14:32.042"), 2, 100, "shaurma", 4),
    BankOperation(Timestamp.valueOf("2019-03-20 12:14:32.042"), 2, 100, "shaurma", 6),
    BankOperation(Timestamp.valueOf("2020-04-03 19:42:00.333"), 1, 340, "cinema", 4),
    BankOperation(Timestamp.valueOf("2020-04-05 04:20:00.000"), 2, 228, "blue kite", 3),
    BankOperation(Timestamp.valueOf("2020-04-05 16:20:00.000"), 1, 199, "food purchase", 3),
    BankOperation(Timestamp.valueOf("2020-04-05 16:20:00.000"), 2, 199, "food purchase", 3),
    BankOperation(Timestamp.valueOf("2020-04-05 16:20:00.000"), 1, 199, "food purchase", 4),
    BankOperation(Timestamp.valueOf("2020-04-06 08:43:02.020"), 1, 228, "greetings", 5),
    BankOperation(Timestamp.valueOf("2019-03-20 12:14:32.042"), 1, 1000, "donate", 2),
  )

  // 2.1 Остаток на счёте
  /* данные приводятся к виду:
      Получение отдельно транзакций отправителей с затратами (со знаком минус)
      и получателей с прибылью
      Далее, объединение списков отправителей и получателей
      Их дальнейшая группировка, суммирование прибыли и затрат
      Вывод в консоль
   */
  val receivers = operations.map(operation => (operation.userId, -operation.operation))
  val senders = operations.map(operation => (operation.source, operation.operation))

  val result = receivers ::: senders

  result.groupBy(operation => operation._1).map {
    case (userId, operation) => (userId, operation.map(operation => operation._2).sum)
  }.foreach {
    case (userId, balance) => println(s"User $userId has $balance units")
  }
  separate()

  // 2.2 Наиболее частые операции по наименованию
  /*
      Группировка по наименованиям
      Представление полученной мапы в виде ключ - количество
      Преобразование в список для дальнейшей сортировки по значению
      Реверс для получения сортировки в порядке убывания
      Получение первых 3 значений
      Вывод
  */
  println("Most repeated operations: ")
  operations.groupBy((operation) => (operation.title)).map {
    case (title, operation) => (title, operation.length)
  }.toList
    .sortBy(operation => operation._2)
    .reverse
    .take(3)
    .foreach {
      case (title, times) => println(s"${title} in ${times} times")
    }
  separate()


  // 2.3 В какой части времени больше всего операций
  /*
      Получение коллекции времён
      Группировка по четверть дня - количество операций
      Получение последнего элемента
  */
  def quarter(title: String = ""): Unit = {
    val quad = operations.filter((operation) => operation.title == title).map {
      case (operation) => operation.eventDate
    }.groupBy((day) => (math.floor(day.toLocalDateTime.getHour / 6).toInt + 1)).map {
      case (quarter, day) => (quarter, day.length)
    }.maxBy(quarter => quarter._2)

    println(s"The most count of operation ${title} does at ${quad._1} quarter of day in amount of ${quad._2} units")
  }

  quarter("cinema")
  separate()


  // 3 Затраты по полам
  /*
      Группировка по ЮзерИД - имеют значения 1 и 2 в соответствии с полом
      Далее фильтр полученных значений по наличию их значения ИД в массиве
      Вывод
   */
  val man = "man"
  val woman = "woman"
  val userIdToGender: Map[Int, String] = Map[Int, String](1 -> man, 2 -> woman)

  operations.groupBy(operation => operation.userId).map {
    case (userId, operation) => (userId, operation.map(operation => operation.operation).sum)
  }.foreach {
    case (userId, operation) => println(s"${userIdToGender(userId)}'s costs in ${operation} units")
  }
  separate()


  // 4 Статистика
  /*
      Группировка сразу по наименованиям из списка
      Представление в виде ключ: значение (через внутренню группировку по наименованиям, далее сумма всех расходов)
      Для каждого полученного значения для каждого значения массива типов выводятся данные
      также проверяется их наличие (наличие ключа)
   */
  val types: Set[String] = Set("food purchase", "cinema")

  operations.filter(operation => types.contains(operation.title))
    .groupBy(operation => operation.userId)
    .map {
      case (userId, operation) => (userId, operation
        .map(operation => (operation.title, operation.operation))
        .groupBy(operation => operation._1)
        .map {
          case (title, operation) => (title, operation.map(operation => operation._2).sum)
        })
    }.foreach {
    case (userId, operations) =>
      for (tpe <- types)
        println(s"User's $userId costs for ${tpe} are equal ${if (!operations.contains(tpe)) 0 else operations(tpe)} units")
  }
}

case class BankOperation(eventDate: Timestamp, userId: Int, operation: Double, title: String, source: Int)