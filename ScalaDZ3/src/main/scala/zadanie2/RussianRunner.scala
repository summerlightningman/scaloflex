package zadanie2

case class RussianRunner(override val name: String) extends Runner {
  override def run(x: Double): Double = 2 * math.pow(x, 3) + 3 * math.sqrt(x) - (1 / math.pow(x, 2)) + 4
}
