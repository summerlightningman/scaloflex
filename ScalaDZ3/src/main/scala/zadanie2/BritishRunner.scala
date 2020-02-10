package zadanie2

case class BritishRunner(override val name: String) extends Runner {
  override def run(x: Double): Double = 1 / math.pow(math.cos(x), 2)

}
