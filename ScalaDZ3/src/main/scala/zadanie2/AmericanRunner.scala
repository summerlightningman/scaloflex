package zadanie2

case class AmericanRunner(override val name: String) extends Runner {
  override def run(x: Double): Double = -1 / math.pow(math.sin(x), 2)
}
