package zadanie2

object Main {
  def main(args: Array[String]): Unit = {
    val motocycler = new First
    val carmaster = new Second
    val quadrocycler = new Third
    val pilot = new Fourth
    println(race(motocycler))
    println(race(carmaster))

  }

  def race(runner: Runner): String = {
    runner match {
      case First() => s"$runner.name финишировал с результатом 2.28"
      case Second() => s"$runner.name финишировал с результатом 2.39"
      case Third() => s"$runner.name финишировал с результатом 1.23.23"
      case Fourth() => s"$runner.name финишировал с результатом 43.32.4"
      case _ => s"На трассу ворвался неизвестный гонщик и прошёл всю гонку всего за 10 секунд!"
    }
  }
}

trait Runner {
  val name: String

  def run: Int
}

case class First() extends Runner {
  override val name: String = "Dimas"

  override def run: Int = 2
}

case class Second() extends Runner {
  override val name: String = "Eugene"

  override def run: Int = 2
}

case class Third() extends Runner {
  override val name: String = "Yarik"

  override def run: Int = 8
}

case class Fourth() extends Runner {
  override val name: String = "Serega"

  override def run: Int = 3
}

