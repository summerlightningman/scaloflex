package zadanie2_4

import java.sql.Timestamp

object Main extends App {
  val operations: List[BankOperation] = List[BankOperation](
    BankOperation(Timestamp.valueOf("2019-05-05 13:33:50.234"), 0, 500, "input", 2),
    BankOperation(Timestamp.valueOf("2019-05-05 14:33:50.234"), 1, 1500, "input", 0),
    BankOperation(Timestamp.valueOf("2019-05-05 17:00:00.555"), 0, 300, "transfer", 1),
    BankOperation(Timestamp.valueOf("2019-05-05 17:00:00.555"), 1, -300, "transfer", 0),
    BankOperation(Timestamp.valueOf("2019-05-05 18:00:00.555"), 1, -1000, "food purchase", 3),
    BankOperation(Timestamp.valueOf("2019-04-03 19:42:00.333"), 2, 340, "cinema", 5),
    BankOperation(Timestamp.valueOf("2019-03-20 12:14:32.042"), 2, 100, "shaurma", 3),
    BankOperation(Timestamp.valueOf("2020-04-03 19:42:00.333"), 1, 340, "cinema", 5),
    BankOperation(Timestamp.valueOf("2020-04-05 04:20:00.000"), 2, 228, "blue kite", 1),
    BankOperation(Timestamp.valueOf("2020-04-05 16:20:00.000"), 10, 199, "food purchase", 3),
    BankOperation(Timestamp.valueOf("2020-04-06 08:43:02.020"), 1, 228, "food purchase", 3)
  )

  // 2.1 Остаток на счёте
  /* данные приводятся к виду:
  сначала ((отправитель, получатель), деньги (общая сумма транзакций друг другу)),
  далее (отправитель, -деньги), (получатель, деньги)
  далее (отправитель, -деньги), (получатель, деньги)
  далее, объединение данных, приведение к виду ИД -> Деньги
  вывод
   */
  operations.groupBy((operation: BankOperation) => (operation.userId, operation.source)).map {
    case (users, operation) => (users, operation.map(operation => operation.operation).sum)
  }.map {
    case ((userId, source), operation) => List((userId, -operation), (source, operation))
  }.flatten.groupBy((transaction) => transaction._1).map {
    case (user, transaction) => (user, transaction.map {
      case (_, operation) => operation
    }.sum)
  }.foreach {
    case (user, money) => println(s"Остаток пользователя $user: $money у.е")
  }

  // 2.2 Наиболее частые операции по наименованию
  /*
  Сначала группировка по наименованиям
  Далее представление наименование - количество (было наименование - экземпляр класса операция)
  Преобразование в список (для возможности сортировки), сортировка по 2 параметру (количеству),
  обращение полученного списка в обратный порядок и получение первых 3 значений
  Вывод на экран каждого оставшегося элемента
  */
  println("Наиболее частые операции по типу: ")
  operations.groupBy((operation: BankOperation) => (operation.title)).map {
    case (title, operation) => (title, operation.map((o: BankOperation) => o.title).length)
  }.toList.sortBy(operation => operation._2).reverse.take(3)
    .foreach { case (title, times) => println(s"$title - $times раз(а)") }


  // 2.3 В какой части времени больше всего операций
  /*
  Получение коллекции времён
  Группировка по четверть дня - количество операций
  Получение последнего элемента
  */
  val operation = operations.map {
    case (operation) => operation.eventDate
  }.groupBy((day) => (day.toLocalDateTime.getHour / 4)).map {
    case (quarter, day) => (quarter, day.length)
  }.last
  println(s"Наибольшее количество операций происходит в ${operation._1} четверти дня в количестве ${operation._2}")

  // 3 Статистика по полам
  /*
      Поиск суммы операций каждого пользователя
      Далее фильтр полученных значений по наличию их значения ИД в массиве
      Вывод
   */
  val man = "Мужчин"
  val woman = "Женщин"
  val userIdToGender: Map[Int, String] = Map[Int, String](1 -> man, 2 -> woman)

  operations.groupBy((operation: BankOperation) => operation.userId).map {
    case (userId, operation) => (userId, operation.map {
      (operation) => operation.operation
    }.sum)
  }.filter {
    (transaction) => userIdToGender.contains(transaction._1)
  }.foreach {
    case (userId, operation) =>
      println(s"${userIdToGender(userId)}ы тратят $operation у.е")
  }

  // 4 Статистика
  val types: Set[String] = Set("food purchase", "cinema")

  operations.groupBy((operation) => operation.userId).map {
    case (userId, operation) => (userId, operation.map(operation => (operation.title, operation.operation))
      .filter(operation => types.contains(operation._1))
      .groupBy { case (title, operation) => title }
      .map {
        case (title, operation) => (title, operation.map {
          case (title, operation) => operation
        }.sum)
      })
  }.foreach {
    case (userId, operation) =>
      val food: Double = if (operation.contains("food purchase")) operation("food purchase") else 0
      val cinema: Double = if (operation.contains("cinema")) operation("cinema") else 0
      println(s"Пользователь $userId потратил $food у.е. на еду и $cinema на поход в кино")
  }


}

case class BankOperation(eventDate: Timestamp, userId: Int, operation: Double, title: String, source: Int)




